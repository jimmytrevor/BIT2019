package com.example.resourcesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.resourcesapp.Fragments.AccountFragment;
import com.example.resourcesapp.Fragments.FavouriteFragment;
import com.example.resourcesapp.Fragments.HomeFragment;
import com.example.resourcesapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_view);


        setFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int selected_item_id=item.getItemId();
                if (selected_item_id == R.id.action_home){
                    //inflate the home fragment
                    setFragment(new HomeFragment());
                }
                else if (selected_item_id == R.id.action_favorite){
                    //inflate the favourite fragment
                    setFragment(new FavouriteFragment());
                }
                else if (selected_item_id == R.id.action_profile){
                    //inflate the profile fragment
                    setFragment(new AccountFragment());
                }
                else{

                }
                return false;
            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame,fragment);
        transaction.commit();
    }
}