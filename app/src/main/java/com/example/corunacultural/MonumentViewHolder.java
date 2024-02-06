package com.example.corunacultural;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MonumentViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;  // Vista para mostrar la imagen del elemento
    public TextView text;     // Vista para mostrar el texto del elemento


    // Constructor que recibe la vista de un elemento
    public MonumentViewHolder(@NonNull View itemView) {
        super(itemView);

        // Asocia las vistas de la interfaz de usuario con las variables miembro
        image = itemView.findViewById(R.id.monumentImage);  // Asocia la ImageView
        text = itemView.findViewById(R.id.monumentName);      // Asocia la TextView
    }

    // Método para mostrar datos en las vistas de la interfaz de usuario
    public void showData(MonumentItem data, Activity activity) {
        text.setText(data.getItemName());  // Configura el texto de la TextView con el nombre del elemento

        // Utiliza la biblioteca Glide para cargar la imagen desde la URL y mostrarla en la ImageView
        Glide.with(itemView.getContext())
                .load(data.getImageUrl())  // Obtiene la URL de la imagen desde el objeto MyItem
                .into(image);              // Asigna la imagen a la ImageView
    }
}
