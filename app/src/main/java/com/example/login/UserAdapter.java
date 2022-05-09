package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

public class UserAdapter extends AppCompatActivity {

    private Context context;
    private int Layout;
    private List<User> todoUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://signinup-6bab5-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("todo");

    public UserAdapter(Context context, int layout, List<User> todoList) {
        this.context = context;
        Layout = layout;
        this.todoUser = todoList;
    }
    
    public int getCount() {
        return todoUser.size();
    }

    public Object getItem(int i) {
        return null;
    }


    public long getItemId(int i) {
        return 0;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(Layout, null);

        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtEmail = view.findViewById(R.id.txtEmail);
        User item = todoUser.get(i);
        txtName.setText(item.getName());
        txtEmail.setText(item.getEmail());

        // random color
        Random rnd = new Random();
        ConstraintLayout ctl = view.findViewById(R.id.layOutItem);
        // Add Switch Change



        //Button remove
        ImageButton btnRemove = view.findViewById(R.id.btnDelete);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(todoUser.get(i).getId() + "").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Xoa thanh cong", "");
                            Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Error !", "");
                        }
                    }
                });
            }
        });

        return view;
    }

    public void notifyDataSetChanged() {
    }
}