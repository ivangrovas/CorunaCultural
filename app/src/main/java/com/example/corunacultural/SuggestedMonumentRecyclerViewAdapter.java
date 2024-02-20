package com.example.corunacultural;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedMonumentRecyclerViewAdapter extends RecyclerView.Adapter<SuggestedMonumentViewHolder> {
    private List<MonumentData> allSuggestedMonuments;
    private Activity activity;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public SuggestedMonumentRecyclerViewAdapter(List<MonumentData> monumentSet, Activity activity){
        this.allSuggestedMonuments = monumentSet;
        this.activity = activity;
    }
    @NonNull
    @Override
    public SuggestedMonumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_suggested_monument_view_holder, parent, false);
        return new SuggestedMonumentViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SuggestedMonumentViewHolder holder, int position) {
        // Obtener los datos del monumento en la posición actual
        MonumentData dataInPositionToBeRendered = allSuggestedMonuments.get(position);
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
        return allSuggestedMonuments.size();
    }
}
