package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ActivityLogin extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button btnSignin;
    private TextView txtRegis;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        btnSignin = findViewById(R.id.btnSingIn);
        txtRegis = findViewById(R.id.txtRegister);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        txtRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityLogin.this,ActivityRegister.class);
                startActivity(i);
            }
        });
    }


    private void Login() {
        String emailLG, passLG;
        emailLG = email.getText().toString();
        passLG = password.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(emailLG).matches()){
            Toast.makeText(this,"Email khong hop le !",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(passLG)){
            Toast.makeText(this,"Vui long nhap password !",Toast.LENGTH_SHORT).show();
            return;

        }
        mAuth.signInWithEmailAndPassword(emailLG,passLG).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Dang nhap thanh cong!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityLogin.this,MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Sai email hoac mat khau!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}