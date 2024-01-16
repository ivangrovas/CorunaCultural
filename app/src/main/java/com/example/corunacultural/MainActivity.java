package com.example.corunacultural;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent firstTimeSC = new Intent(MainActivity.this, FirstTimeSC.class);
        startActivity(firstTimeSC);
        finish();
    }
}