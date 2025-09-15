package com.azotec.matchmaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GoleadoresFragment extends Fragment {

    private RecyclerView rvGoleadores;
    private GoleadoresAdapter adapter;
    private TextView tvGoleadoresVacio;
    private Button btnAgregarGoleador;
    private List<Goleador> goleadores;

    public static GoleadoresFragment newInstance() {
        return new GoleadoresFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goleadores = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goleadores, container, false);

        rvGoleadores = view.findViewById(R.id.rv_goleadores);
        tvGoleadoresVacio = view.findViewById(R.id.tv_goleadores_vacio);
        btnAgregarGoleador = view.findViewById(R.id.btn_agregar_goleador);

        configurarRecyclerView();
        configurarBotones();
        actualizarVisibilidad();

        return view;
    }

    private void configurarRecyclerView() {
        adapter = new GoleadoresAdapter(goleadores, new GoleadoresAdapter.OnGoleadorEditListener() {
            @Override
            public void onGoleadorEdit(Goleador goleador, int position) {
                // Aquí se puede implementar un diálogo de edición si se desea
                // Por ahora, el adapter maneja la edición inline
            }

            @Override
            public void onGoleadorDelete(int position) {
                eliminarGoleador(position);
            }
        });
        rvGoleadores.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGoleadores.setAdapter(adapter);
    }

    private void configurarBotones() {
        btnAgregarGoleador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarNuevoGoleador();
            }
        });
    }

    private void agregarNuevoGoleador() {
        Goleador nuevoGoleador = new Goleador("", 0);
        goleadores.add(nuevoGoleador);
        adapter.notifyItemInserted(goleadores.size() - 1);
        actualizarVisibilidad();
    }

    private void eliminarGoleador(int position) {
        if (position >= 0 && position < goleadores.size()) {
            goleadores.remove(position);
            adapter.notifyItemRemoved(position);
            actualizarVisibilidad();
        }
    }

    private void actualizarVisibilidad() {
        if (goleadores.isEmpty()) {
            tvGoleadoresVacio.setVisibility(View.VISIBLE);
            rvGoleadores.setVisibility(View.GONE);
        } else {
            tvGoleadoresVacio.setVisibility(View.GONE);
            rvGoleadores.setVisibility(View.VISIBLE);
        }
    }

    public void actualizarGoleadores(List<Goleador> nuevosGoleadores) {
        if (nuevosGoleadores != null) {
            goleadores.clear();
            goleadores.addAll(nuevosGoleadores);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                actualizarVisibilidad();
            }
        }
    }

    public List<Goleador> getGoleadores() {
        return goleadores;
    }
} 