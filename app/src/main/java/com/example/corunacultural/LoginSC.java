package com.example.corunacultural;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LoginSC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sc);

        Intent mainSC = new Intent(LoginSC.this, MainSC.class);
        startActivity(mainSC);
    }
}