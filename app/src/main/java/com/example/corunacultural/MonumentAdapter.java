package com.example.corunacultural;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MonumentAdapter extends RecyclerView.Adapter<MonumentViewHolder> {
    private List<MonumentItem> itemList;
    private Activity activity;

    public MonumentAdapter(List<MonumentItem> itemList, Activity activity){
        this.itemList = itemList;
        this.activity = activity;
    }

    // Método llamado cuando se necesita crear un nuevo ViewHolder
    @NonNull
    @Override
    public MonumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el diseño de cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_favourite_album_item, parent, false);
        return new MonumentViewHolder(view);  // Esta línea invoca el constructor del ViewHolder
    }

    // Método llamado cuando se debe asociar un ViewHolder a un elemento específico en la lista
    @Override
    public void onBindViewHolder(MonumentViewHolder holder, int position) {
        MonumentItem myItem = itemList.get(position);
        holder.showData(myItem, activity);  // Llama al método showData del ViewHolder para mostrar los datos

    }

    // Método que devuelve la cantidad total de elementos en la lista
    @Override
    public int getItemCount() {
        return itemList.size();
    }

}