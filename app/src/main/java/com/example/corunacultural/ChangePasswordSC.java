package com.example.corunacultural;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ChangePasswordSC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_sc);

        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item1){
                    Intent intent1 = new Intent(ChangePasswordSC.this,MainSC.class);
                    startActivity(intent1);
                }
                if (item.getItemId() == R.id.item2){
                    Intent intent2 = new Intent(ChangePasswordSC.this,BestRatedSC.class);
                    startActivity(intent2);
                }
                if (item.getItemId() == R.id.item3){
                    Intent intent3 = new Intent(ChangePasswordSC.this,FavouriteSC.class);
                    startActivity(intent3);
                }
                
                return true;
            }
        });
    }
}