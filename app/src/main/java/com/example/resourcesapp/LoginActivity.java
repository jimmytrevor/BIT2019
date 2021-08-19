package com.example.resourcesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

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
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("UserImages");
        dialog = new ProgressDialog(LoginActivity.this);

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

    public  void signInWithEmailAndPassword(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     Toast.makeText(LoginActivity.this,"Login was successful ", Toast.LENGTH_LONG).show();
                 }
                 else{
                     Toast.makeText(LoginActivity.this,"Wrong Credentials", Toast.LENGTH_LONG).show();
                 }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(LoginActivity.this,"Error "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createUserWithEmailAndPassword(String email, String password, String name,int age, String address,Uri imagePathUri){

        dialog.setMessage("Creating Signing.....");
        dialog.show();
         auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if (task.isSuccessful()){
                     final  StorageReference imageRef = storageReference.child(""+imagePathUri.getLastPathSegment());

                     imageRef.putFile(imagePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             dialog.setMessage("Getting the download Uri");
                             imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {

                                     DatabaseReference finalRef=databaseReference.push();
                                     finalRef.child("fullname").setValue(name);
                                     finalRef.child("age").setValue(age);
                                     finalRef.child("address").setValue(address);
                                     finalRef.child("image").setValue(String.valueOf(uri));
                                     finalRef.child("email").setValue(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {
                                             Toast.makeText(LoginActivity.this, "User account created successfully", Toast.LENGTH_SHORT).show();
                                             dialog.dismiss();
                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull  Exception e) {
                                             Toast.makeText(LoginActivity.this,"Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                                             dialog.dismiss();
                                         }
                                     });

                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull  Exception e) {
                                     Toast.makeText(LoginActivity.this,"Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                                     dialog.dismiss();
                                 }
                             });

                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(LoginActivity.this,"Error "+e.getMessage(), Toast.LENGTH_LONG).show();
                             dialog.dismiss();
                         }
                     }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                             int progress= (int)((snapshot.getBytesTransferred()/snapshot.getTotalByteCount())*100);
                             dialog.setMessage("Uploading image at "+progress+"%");
                         }
                     });
                 }
                 else{

                 }
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull  Exception e) {
                 Toast.makeText(LoginActivity.this,"Error "+e.getMessage(), Toast.LENGTH_LONG).show();
             }
         });
    }






}