package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLi extends AppCompatActivity {

    ListView lvQuanLi;
    ArrayList<User> arrUser;
    UserAdapter adapter;


    FirebaseDatabase database = FirebaseDatabase.getInstance("https://signinup-6bab5-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("todo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li);
        initUI();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText name = findViewById(R.id.name);
                EditText email = findViewById(R.id.email);
                int length = arrUser.size();
                User user = new User(name.getText().toString(),email.getText().toString());
//                Todo todo = new Todo(edtTodo.getText().toString(), 0);
//                todo.setId(length);
//
//                String pathOject = String.valueOf(todo.getId());

                myRef.child("users").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(QuanLi.this, "Add success", Toast.LENGTH_SHORT).show();
                        loadDataFromFirebase();
                    }
                });
            }
        });
    }

    private void initUI() {
        lvQuanLi = findViewById(R.id.lvMain);
        arrUser = new ArrayList<>();

        loadDataFromFirebase();

        adapter = new UserAdapter(this, R.layout.activity_item_email, arrUser);
        lvQuanLi.setAdapter((ListAdapter) adapter);
    }

    private void loadDataFromFirebase () {
        // Load data to arrTodo/ Async
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> listUser = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    listUser.add(user);
                }
                arrUser.clear();
                arrUser.addAll(listUser);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}