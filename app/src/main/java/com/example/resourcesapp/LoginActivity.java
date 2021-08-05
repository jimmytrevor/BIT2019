package com.example.resourcesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.resourcesapp.Activity.HomeActivity;

import static com.example.resourcesapp.Activity.RegisterActivity.fullname_key;
import static com.example.resourcesapp.Activity.RegisterActivity.password_key;
import static com.example.resourcesapp.Activity.RegisterActivity.shared_db;
import static com.example.resourcesapp.Activity.RegisterActivity.username_key;

public class LoginActivity extends AppCompatActivity {
    public  static  final  int PICK_IMAGE_CODE =100;
    private Button pick_image;
    private ImageView imageView;
    private Uri imageUri;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pick_image=findViewById(R.id.btn_pick);
        imageView=findViewById(R.id.picked_image_view);

        sharedPreferences = getSharedPreferences(shared_db, Context.MODE_PRIVATE);

        String passed_message=getIntent().getStringExtra(getResources().getString(R.string.msg_variable));
        String passed_name=getIntent().getStringExtra(getResources().getString(R.string.user_name));
        Toast.makeText(this, ""+passed_message, Toast.LENGTH_SHORT).show();

        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }
    public  void  pickImage(){
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent,"Select Image"),PICK_IMAGE_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode  == PICK_IMAGE_CODE && resultCode == RESULT_OK && data.getData() != null){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_SHORT).show();
    }

    public  void  autheticateUser(String username, String password){
        String stored_username=sharedPreferences.getString(username_key,"");
        String stored_password=sharedPreferences.getString(password_key,"");


        if (username.equals(stored_username) && password.equals(stored_password)){
            //user has logged in
//            Toast.makeText(this, "Hey, "+stored_fullname+" Welcome back", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        else {
            //Wrong Information

        }


    }
}