package com.azotec.matchmaster;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GoleadoresAdapter extends RecyclerView.Adapter<GoleadoresAdapter.GoleadorViewHolder> {

    private List<Goleador> goleadores;
    private OnGoleadorEditListener editListener;

    public interface OnGoleadorEditListener {
        void onGoleadorEdit(Goleador goleador, int position);
        void onGoleadorDelete(int position);
    }

    public GoleadoresAdapter(List<Goleador> goleadores, OnGoleadorEditListener editListener) {
        this.goleadores = goleadores;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public GoleadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goleador, parent, false);
        return new GoleadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoleadorViewHolder holder, int position) {
        Goleador goleador = goleadores.get(position);
        holder.bind(goleador, position + 1, editListener);
    }

    @Override
    public int getItemCount() {
        return goleadores.size();
    }

    public void actualizarGoleadores(List<Goleador> nuevosGoleadores) {
        this.goleadores = nuevosGoleadores;
        notifyDataSetChanged();
    }

    static class GoleadorViewHolder extends RecyclerView.ViewHolder {
        private EditText etNombreJugador;
        private EditText etGolesJugador;
        private TextView tvPosicion;
        private Button btnEliminarGoleador;
        private Goleador goleador;
        private int position;

        public GoleadorViewHolder(@NonNull View itemView) {
            super(itemView);
            etNombreJugador = itemView.findViewById(R.id.et_nombre_jugador);
            etGolesJugador = itemView.findViewById(R.id.et_goles_jugador);
            tvPosicion = itemView.findViewById(R.id.tv_posicion);
            btnEliminarGoleador = itemView.findViewById(R.id.btn_eliminar_goleador);
        }

        public void bind(Goleador goleador, int posicion, OnGoleadorEditListener editListener) {
            this.goleador = goleador;
            this.position = posicion - 1; // Convertir a índice base 0

            tvPosicion.setText(posicion + ".");
            
            // Configurar EditText del nombre
            etNombreJugador.setText(goleador.getNombre());
            etNombreJugador.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    goleador.setNombre(s.toString());
                }
            });

            // Configurar EditText de goles
            etGolesJugador.setText(String.valueOf(goleador.getGoles()));
            etGolesJugador.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int goles = Integer.parseInt(s.toString());
                        goleador.setGoles(goles);
                    } catch (NumberFormatException e) {
                        goleador.setGoles(0);
                    }
                }
            });

            // Configurar botón eliminar
            btnEliminarGoleador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editListener != null) {
                        editListener.onGoleadorDelete(position);
                    }
                }
            });
        }
    }
} 