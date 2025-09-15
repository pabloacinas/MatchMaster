package com.azotec.matchmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClasificacionAdapter extends RecyclerView.Adapter<ClasificacionAdapter.ClasificacionViewHolder> {
    
    private List<Equipo> equipos;
    
    public ClasificacionAdapter(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    @NonNull
    @Override
    public ClasificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clasificacion, parent, false);
        return new ClasificacionViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ClasificacionViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.bind(equipo, position + 1);
    }
    
    @Override
    public int getItemCount() {
        return equipos.size();
    }
    
    public void actualizarEquipos(List<Equipo> nuevosEquipos) {
        this.equipos = nuevosEquipos;
        notifyDataSetChanged();
    }
    
    static class ClasificacionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPosicion;
        private TextView tvNombreEquipo;
        private TextView tvPropietario;
        private TextView tvPuntos;
        private TextView tvPartidosJugados;
        private TextView tvVictorias;
        private TextView tvDerrotas;
        private TextView tvDiferenciaGoles;
        
        public ClasificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosicion = itemView.findViewById(R.id.tv_posicion);
            tvNombreEquipo = itemView.findViewById(R.id.tv_nombre_equipo);
            tvPropietario = itemView.findViewById(R.id.tv_propietario);
            tvPuntos = itemView.findViewById(R.id.tv_puntos);
            tvPartidosJugados = itemView.findViewById(R.id.tv_partidos_jugados);
            tvVictorias = itemView.findViewById(R.id.tv_victorias);
            tvDerrotas = itemView.findViewById(R.id.tv_derrotas);
            tvDiferenciaGoles = itemView.findViewById(R.id.tv_diferencia_goles);
        }
        
        public void bind(Equipo equipo, int posicion) {
            tvPosicion.setText(String.valueOf(posicion));
            tvNombreEquipo.setText(equipo.getNombre());
            tvPropietario.setText(equipo.getPropietario());
            tvPuntos.setText(String.valueOf(equipo.getPuntos()));
            tvPartidosJugados.setText(String.valueOf(equipo.getPartidosJugados()));
            tvVictorias.setText(String.valueOf(equipo.getVictorias()));
            tvDerrotas.setText(String.valueOf(equipo.getDerrotas()));
            tvDiferenciaGoles.setText(String.valueOf(equipo.getDiferenciaGoles()));
            
            // Cambiar color según el propietario
            if (equipo.getPropietario().contains("Usuario 1") || equipo.getPropietario().contains("usuario1")) {
                tvNombreEquipo.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_light));
            } else {
                tvNombreEquipo.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_blue_light));
            }
        }
    }
} 