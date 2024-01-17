package com.example.corunacultural;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainSC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sc);
        Toast.makeText(this, "Main Screen iniciada!", Toast.LENGTH_SHORT).show();
    }
}