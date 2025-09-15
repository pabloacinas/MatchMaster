package com.azotec.matchmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {
    
    private List<Equipo> equipos;
    
    public EquipoAdapter(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_equipo, parent, false);
        return new EquipoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.bind(equipo);
    }
    
    @Override
    public int getItemCount() {
        return equipos.size();
    }
    
    public void actualizarEquipos(List<Equipo> nuevosEquipos) {
        this.equipos = nuevosEquipos;
        notifyDataSetChanged();
    }
    
    static class EquipoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreEquipo;
        private TextView tvPropietario;
        
        public EquipoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreEquipo = itemView.findViewById(R.id.tv_nombre_equipo);
            tvPropietario = itemView.findViewById(R.id.tv_propietario);
        }
        
        public void bind(Equipo equipo) {
            tvNombreEquipo.setText(equipo.getNombre());
            tvPropietario.setText("Propietario: " + equipo.getPropietario());
            
            // Cambiar color según el propietario
            if (equipo.getPropietario().contains("Usuario 1") || equipo.getPropietario().contains("usuario1")) {
                tvNombreEquipo.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
            } else {
                tvNombreEquipo.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_blue_dark));
            }
        }
    }
} 