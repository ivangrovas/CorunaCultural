package com.example.corunacultural;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstTimeSC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_sc);
        Button signInBT = findViewById(R.id.signInButton);
        Button signUpBT = findViewById(R.id.signUpButton);

        signInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(FirstTimeSC.this, LoginSC.class);
                startActivity(signInIntent);
            }
        });

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(FirstTimeSC.this, RegisterSC.class);
                startActivity(signUpIntent);
            }
        });
    }
}