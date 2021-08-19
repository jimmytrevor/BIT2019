package com.example.resourcesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resourcesapp.LoginActivity;
import com.example.resourcesapp.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullname;
    private EditText username;
    private EditText password;
    private Button register;
    private EditText age;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String shared_db="app_data";
    public static final String username_key="username";
    public static final String fullname_key="fullname";
    public static final String password_key="password";
    public static final String age_key="age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        age=findViewById(R.id.age);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register_btn);
        sharedPreferences = getSharedPreferences(shared_db, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(fullname.getText()) || fullname.getText().toString().trim().length() <3){
                    Toast.makeText(RegisterActivity.this, "Please Enter a valid name", Toast.LENGTH_SHORT).show();
                }
                else{
//                    Store user information using SharedPreferences
                    String name=fullname.getText().toString().trim();
                    String usern=username.getText().toString().trim();
                    String pass=password.getText().toString().trim();
                    int ag=Integer.parseInt(age.getText().toString().trim());
                    editor.putString(fullname_key,name);
                    editor.putString(username_key,usern);
                    editor.putString(password_key,pass);
                    editor.putInt(age_key,ag);
                    editor.apply();
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });

    }
}