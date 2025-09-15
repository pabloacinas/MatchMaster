package com.azotec.matchmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TraspasosAdapter extends RecyclerView.Adapter<TraspasosAdapter.TraspasoViewHolder> {
    
    private List<Traspaso> traspasos;
    
    public TraspasosAdapter(List<Traspaso> traspasos) {
        this.traspasos = traspasos;
    }
    
    @NonNull
    @Override
    public TraspasoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traspaso, parent, false);
        return new TraspasoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TraspasoViewHolder holder, int position) {
        Traspaso traspaso = traspasos.get(position);
        holder.bind(traspaso);
    }
    
    @Override
    public int getItemCount() {
        return traspasos.size();
    }
    
    public void actualizarTraspasos(List<Traspaso> nuevosTraspasos) {
        this.traspasos = nuevosTraspasos;
        notifyDataSetChanged();
    }
    
    static class TraspasoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJornadaTraspaso;
        private TextView tvTextoTraspaso;
        
        public TraspasoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJornadaTraspaso = itemView.findViewById(R.id.tv_jornada_traspaso);
            tvTextoTraspaso = itemView.findViewById(R.id.tv_texto_traspaso);
        }
        
        public void bind(Traspaso traspaso) {
            tvJornadaTraspaso.setText(traspaso.getJornadaTexto());
            tvTextoTraspaso.setText(traspaso.getTexto());
        }
    }
} 