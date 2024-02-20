package com.example.corunacultural;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SuggestedMonumentViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;

    public SuggestedMonumentViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.monumentImage);
        textView = (TextView) itemView.findViewById(R.id.monumentName);
    }
    // Método para mostrar datos en el ViewHolder
    public void showData(MonumentData data, Activity activity) {
        // Establecer el texto en el TextView con el nombre del animal
        textView.setText(data.getName());

        // Utilizar la biblioteca Glide para cargar la imagen desde la URL y mostrarla en el ImageView
        Glide.with(itemView.getContext())
                .load(data.getImageURL())
                .into(imageView);
    }
}
