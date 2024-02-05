package com.example.corunacultural;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileSC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sc);

        Button seeMore1BT = findViewById(R.id.changePwd);
        seeMore1BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeMore1Intent = new Intent(ProfileSC.this, ChangePasswordSC.class);
                startActivity(seeMore1Intent);
            }
        });

        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item1){
                    Intent intent1 = new Intent(ProfileSC.this,MainSC.class);
                    startActivity(intent1);
                }
                if (item.getItemId() == R.id.item2){
                    Intent intent2 = new Intent(ProfileSC.this,BestRatedSC.class);
                    startActivity(intent2);
                }
                if (item.getItemId() == R.id.item3){
                    Intent intent3 = new Intent(ProfileSC.this,FavouriteSC.class);
                    startActivity(intent3);
                }
                if (item.getItemId() == R.id.item4){
                    Intent intent4 = new Intent(ProfileSC.this, ChangePasswordSC.class);
                    startActivity(intent4);
                }
                return true;
            }
        });
    }
}