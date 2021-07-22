package com.example.resourcesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public  static  final  int PICK_IMAGE_CODE =100;
    private Button pick_image;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pick_image=findViewById(R.id.btn_pick);
        imageView=findViewById(R.id.picked_image_view);

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
}