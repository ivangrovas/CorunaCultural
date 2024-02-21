package com.example.corunacultural;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailSC extends AppCompatActivity {

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sc);

        // Obtención de la información del intent
        FavouriteData favData = getIntent().getParcelableExtra("monument");

        ImageView imageView = findViewById(R.id.ImageViewAC);
        TextView textTitle = findViewById(R.id.textViewTitle);
        TextView textRating = findViewById(R.id.textDescription);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(DetailSC.this, "Ha votado con: "+rating, Toast.LENGTH_SHORT).show();
            }
        });



        // Mostrar la información de la UI
        textTitle.setText(favData.getName());
        textRating.setText(String.valueOf(favData.getRating())); // Convertir a String el valor numérico del rating

            // Utilizar Glide para cargar la imagen desde la URL y mostrarla en el ImageView
        Glide.with(this)
                .load(favData.getImageURL())
                .into(imageView);
    }

}