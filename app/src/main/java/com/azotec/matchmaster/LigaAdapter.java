package com.azotec.matchmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LigaAdapter extends RecyclerView.Adapter<LigaAdapter.LigaViewHolder> {
    
    private List<Liga> ligas;
    private OnLigaClickListener listener;
    private OnLigaEliminarListener eliminarListener;
    
    public interface OnLigaClickListener {
        void onLigaClick(Liga liga);
    }
    
    public interface OnLigaEliminarListener {
        void onLigaEliminar(Liga liga);
    }
    
    public LigaAdapter(List<Liga> ligas) {
        this.ligas = ligas;
    }
    
    public void setOnLigaClickListener(OnLigaClickListener listener) {
        this.listener = listener;
    }
    
    public void setOnLigaEliminarListener(OnLigaEliminarListener eliminarListener) {
        this.eliminarListener = eliminarListener;
    }
    
    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_liga, parent, false);
        return new LigaViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        Liga liga = ligas.get(position);
        holder.bind(liga);
    }
    
    @Override
    public int getItemCount() {
        return ligas.size();
    }
    
    public void actualizarLigas(List<Liga> nuevasLigas) {
        this.ligas = nuevasLigas;
        notifyDataSetChanged();
    }
    
    class LigaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreLiga;
        private TextView tvDescripcionLiga;
        private TextView tvNumEquipos;
        private Button btnEliminarLiga;
        
        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreLiga = itemView.findViewById(R.id.tv_nombre_liga);
            tvDescripcionLiga = itemView.findViewById(R.id.tv_descripcion_liga);
            tvNumEquipos = itemView.findViewById(R.id.tv_num_equipos);
            btnEliminarLiga = itemView.findViewById(R.id.btn_eliminar_liga);
        }
        
        public void bind(Liga liga) {
            tvNombreLiga.setText(liga.getNombre());
            
            // Mostrar descripción solo si no está vacía
            if (liga.getDescripcion() != null && !liga.getDescripcion().trim().isEmpty()) {
                tvDescripcionLiga.setText(liga.getDescripcion());
                tvDescripcionLiga.setVisibility(View.VISIBLE);
            } else {
                tvDescripcionLiga.setVisibility(View.GONE);
            }
            
            tvNumEquipos.setText(liga.getNumEquipos() + " equipos");
            
            // Configurar botón eliminar
            btnEliminarLiga.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eliminarListener != null) {
                        eliminarListener.onLigaEliminar(liga);
                    }
                }
            });
            
            // Hacer el item clickeable (excepto el botón eliminar)
            View.OnClickListener itemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onLigaClick(liga);
                    }
                }
            };
            
            // Aplicar click listener a todos los elementos excepto el botón eliminar
            tvNombreLiga.setOnClickListener(itemClickListener);
            tvDescripcionLiga.setOnClickListener(itemClickListener);
            tvNumEquipos.setOnClickListener(itemClickListener);
            
            // El itemView principal no necesita click listener ya que los elementos individuales lo tienen
        }
    }
} 