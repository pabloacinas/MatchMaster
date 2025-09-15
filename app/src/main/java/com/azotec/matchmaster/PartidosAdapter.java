package com.azotec.matchmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PartidosAdapter extends RecyclerView.Adapter<PartidosAdapter.PartidoViewHolder> {
    
    private List<Partido> partidos;
    private OnPartidoClickListener listener;
    
    public interface OnPartidoClickListener {
        void onPartidoClick(Partido partido, int position);
    }
    
    public PartidosAdapter(List<Partido> partidos) {
        this.partidos = partidos;
    }
    
    public void setOnPartidoClickListener(OnPartidoClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_partido, parent, false);
        return new PartidoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {
        Partido partido = partidos.get(position);
        holder.bind(partido);
    }
    
    @Override
    public int getItemCount() {
        return partidos.size();
    }
    
    public void actualizarPartidos(List<Partido> nuevosPartidos) {
        this.partidos = nuevosPartidos;
        notifyDataSetChanged();
    }
    
    class PartidoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEquipoLocal;
        private TextView tvEquipoVisitante;
        private TextView tvResultado;
        private TextView tvEstado;
        private TextView tvTraspaso;
        private TextView tvGoleadoresLocal;
        private TextView tvGoleadoresVisitante;

        public PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEquipoLocal = itemView.findViewById(R.id.tv_equipo_local);
            tvEquipoVisitante = itemView.findViewById(R.id.tv_equipo_visitante);
            tvResultado = itemView.findViewById(R.id.tv_resultado);
            tvEstado = itemView.findViewById(R.id.tv_estado);
            tvTraspaso = itemView.findViewById(R.id.tv_traspaso);
            tvGoleadoresLocal = itemView.findViewById(R.id.tv_goleadores_local);
            tvGoleadoresVisitante = itemView.findViewById(R.id.tv_goleadores_visitante);
        }
        
        public void bind(Partido partido) {
            tvEquipoLocal.setText(partido.getEquipoLocal().getNombre());
            tvEquipoVisitante.setText(partido.getEquipoVisitante().getNombre());
            
            if (partido.isJugado()) {
                tvResultado.setText(partido.getGolesLocal() + " - " + partido.getGolesVisitante());
                tvEstado.setText(partido.getResultado());
                tvEstado.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
            } else {
                tvResultado.setText("- -");
                tvEstado.setText("Pendiente");
                tvEstado.setTextColor(itemView.getContext().getResources().getColor(android.R.color.darker_gray));
            }
            
            // Mostrar traspaso si existe
            if (partido.getTraspaso() != null && !partido.getTraspaso().trim().isEmpty()) {
                tvTraspaso.setText("Traspaso: " + partido.getTraspaso());
                tvTraspaso.setVisibility(View.VISIBLE);
            } else {
                tvTraspaso.setVisibility(View.GONE);
            }

            // Bindear goleadores
            // Local
            if (partido.getGoleadoresLocal() != null && !partido.getGoleadoresLocal().isEmpty()) {
                tvGoleadoresLocal.setText(partido.getGoleadoresLocal().replace(", ", "\n")); // Reemplaza comas por saltos de línea si es necesario
                tvGoleadoresLocal.setVisibility(View.VISIBLE);
            } else {
                tvGoleadoresLocal.setText(""); // O "-" si prefieres un placeholder
                // Ocultar si no hay goleadores locales, o dejar el espacio si el diseño lo requiere.
                // tvGoleadoresLocal.setVisibility(View.GONE); // Descomentar si quieres ocultarlo completamente
            }

            // Visitante
            if (partido.getGoleadoresVisitante() != null && !partido.getGoleadoresVisitante().isEmpty()) {
                tvGoleadoresVisitante.setText(partido.getGoleadoresVisitante().replace(", ", "\n")); // Reemplaza comas por saltos de línea
                tvGoleadoresVisitante.setVisibility(View.VISIBLE);
            } else {
                tvGoleadoresVisitante.setText(""); // O "-"
                // tvGoleadoresVisitante.setVisibility(View.GONE); // Descomentar si quieres ocultarlo completamente
            }
            
            // Cambiar colores según propietarios
            if (partido.getEquipoLocal().getPropietario().contains("Usuario 1") || 
                partido.getEquipoLocal().getPropietario().contains("usuario1")) {
                tvEquipoLocal.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_light));
            } else {
                tvEquipoLocal.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_blue_light));
            }
            
            if (partido.getEquipoVisitante().getPropietario().contains("Usuario 1") || 
                partido.getEquipoVisitante().getPropietario().contains("usuario1")) {
                tvEquipoVisitante.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_light));
            } else {
                tvEquipoVisitante.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_blue_light));
            }
            
            // Hacer el item clickeable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPartidoClick(partido, getAdapterPosition());
                    }
                }
            });
        }
    }
} 