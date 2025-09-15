package com.azotec.matchmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TorneoAdapter extends RecyclerView.Adapter<TorneoAdapter.TorneoViewHolder> {
    
    private List<Torneo> torneos;
    
    public TorneoAdapter(List<Torneo> torneos) {
        this.torneos = torneos;
    }
    
    @NonNull
    @Override
    public TorneoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_torneo, parent, false);
        return new TorneoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TorneoViewHolder holder, int position) {
        Torneo torneo = torneos.get(position);
        holder.bind(torneo);
    }
    
    @Override
    public int getItemCount() {
        return torneos.size();
    }
    
    public void actualizarTorneos(List<Torneo> nuevosTorneos) {
        this.torneos = nuevosTorneos;
        notifyDataSetChanged();
    }
    
    static class TorneoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreTorneo;
        private TextView tvDescripcionTorneo;
        private TextView tvTipoTorneo;
        private TextView tvNumParticipantes;
        
        public TorneoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreTorneo = itemView.findViewById(R.id.tv_nombre_torneo);
            tvDescripcionTorneo = itemView.findViewById(R.id.tv_descripcion_torneo);
            tvTipoTorneo = itemView.findViewById(R.id.tv_tipo_torneo);
            tvNumParticipantes = itemView.findViewById(R.id.tv_num_participantes);
        }
        
        public void bind(Torneo torneo) {
            tvNombreTorneo.setText(torneo.getNombre());
            tvDescripcionTorneo.setText(torneo.getDescripcion());
            tvTipoTorneo.setText(torneo.getTipo());
            tvNumParticipantes.setText(torneo.getNumParticipantes() + " participantes");
        }
    }
} 