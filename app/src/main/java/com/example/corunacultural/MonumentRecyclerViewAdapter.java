package com.example.corunacultural;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MonumentRecyclerViewAdapter extends RecyclerView.Adapter<MonumentViewHolder> {
    private List<MonumentData> allMonuments;
    private Activity activity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public MonumentRecyclerViewAdapter(List<MonumentData> monumentSet, Activity activity){
        this.allMonuments = monumentSet;
        this.activity = activity;
    }
    @NonNull
    @Override
    public MonumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_monument_view_holder, parent, false);
        return new MonumentViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MonumentViewHolder holder, int position) {
        // Obtener los datos del monumento en la posición actual
        MonumentData dataInPositionToBeRendered = allMonuments.get(position);
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
