package com.azotec.matchmaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClasificacionFragment extends Fragment {
    
    private RecyclerView rvClasificacion;
    private ClasificacionAdapter adapter;
    private List<Equipo> equipos;
    
    public static ClasificacionFragment newInstance(List<Equipo> equipos) {
        ClasificacionFragment fragment = new ClasificacionFragment();
        Bundle args = new Bundle();
        args.putSerializable("equipos", new ArrayList<>(equipos));
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            equipos = (List<Equipo>) getArguments().getSerializable("equipos");
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clasificacion, container, false);
        
        rvClasificacion = view.findViewById(R.id.rv_clasificacion);
        configurarRecyclerView();
        
        return view;
    }
    
    private void configurarRecyclerView() {
        List<Equipo> equiposOrdenados = ordenarEquipos();
        adapter = new ClasificacionAdapter(equiposOrdenados);
        rvClasificacion.setLayoutManager(new LinearLayoutManager(getContext()));
        rvClasificacion.setAdapter(adapter);
    }
    
    public void actualizarClasificacion() {
        if (adapter != null) {
            List<Equipo> equiposOrdenados = ordenarEquipos();
            adapter.actualizarEquipos(equiposOrdenados);
        }
    }
    
    private List<Equipo> ordenarEquipos() {
        List<Equipo> equiposOrdenados = new ArrayList<>(equipos);
        Collections.sort(equiposOrdenados, new Comparator<Equipo>() {
            @Override
            public int compare(Equipo e1, Equipo e2) {
                // Ordenar por puntos (descendente)
                int comparacionPuntos = Integer.compare(e2.getPuntos(), e1.getPuntos());
                if (comparacionPuntos != 0) {
                    return comparacionPuntos;
                }
                
                // Si tienen los mismos puntos, ordenar por diferencia de goles
                int comparacionDiferencia = Integer.compare(e2.getDiferenciaGoles(), e1.getDiferenciaGoles());
                if (comparacionDiferencia != 0) {
                    return comparacionDiferencia;
                }
                
                // Si tienen la misma diferencia, ordenar por goles a favor
                return Integer.compare(e2.getGolesFavor(), e1.getGolesFavor());
            }
        });
        return equiposOrdenados;
    }
} 