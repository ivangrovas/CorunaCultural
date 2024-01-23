package com.example.corunacultural;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouriteRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private List<FavouriteData> allMonuments;
    private Activity activity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public FavouriteRecyclerViewAdapter(List<FavouriteData> monumentSet, Activity activity){
        this.allMonuments = monumentSet;
        this.activity = activity;
    }
    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_favourite_view_holder, parent, false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        // Obtener los datos del monumento en la posición actual
            FavouriteData dataInPositionToBeRendered = allMonuments.get(position);
        // Llamar al método showData del ViewHolder para mostrar los datos en la vista
        holder.showData(dataInPositionToBeRendered, activity);
        final int adapterPosition = holder.getAdapterPosition();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition);
                }
            }
        });
    }
    // Método que devuelve la cantidad total de elementos en la lista de datos
    @Override
    public int getItemCount() {
        return allMonuments.size();
    }
}