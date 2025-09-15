package com.azotec.matchmaster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TraspasosFragment extends Fragment {
    
    private RecyclerView rvTraspasos;
    private TraspasosAdapter adapter;
    private TextView tvTraspasosVacio;
    private List<Traspaso> traspasos;
    
    public static TraspasosFragment newInstance(List<Jornada> jornadas) {
        TraspasosFragment fragment = new TraspasosFragment();
        Bundle args = new Bundle();
        args.putSerializable("jornadas", new ArrayList<>(jornadas));
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            List<Jornada> jornadas = (List<Jornada>) getArguments().getSerializable("jornadas");
            traspasos = extraerTraspasos(jornadas);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traspasos, container, false);
        
        rvTraspasos = view.findViewById(R.id.rv_traspasos);
        tvTraspasosVacio = view.findViewById(R.id.tv_traspasos_vacio);
        
        configurarRecyclerView();
        actualizarVisibilidad();
        
        return view;
    }
    
    private void configurarRecyclerView() {
        adapter = new TraspasosAdapter(traspasos);
        rvTraspasos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTraspasos.setAdapter(adapter);
    }
    
    private List<Traspaso> extraerTraspasos(List<Jornada> jornadas) {
        List<Traspaso> listaTraspasos = new ArrayList<>();
        
        if (jornadas != null) {
            for (Jornada jornada : jornadas) {
                for (Partido partido : jornada.getPartidos()) {
                    if (partido.getTraspaso() != null && !partido.getTraspaso().trim().isEmpty()) {
                        Traspaso traspaso = new Traspaso(
                            jornada.getNumero(),
                            partido.getTraspaso()
                        );
                        listaTraspasos.add(traspaso);
                    }
                }
            }
        }
        
        return listaTraspasos;
    }
    
    private void actualizarVisibilidad() {
        if (traspasos.isEmpty()) {
            tvTraspasosVacio.setVisibility(View.VISIBLE);
            rvTraspasos.setVisibility(View.GONE);
        } else {
            tvTraspasosVacio.setVisibility(View.GONE);
            rvTraspasos.setVisibility(View.VISIBLE);
        }
    }
    
    public void actualizarTraspasos(List<Jornada> nuevasJornadas) {
        traspasos = extraerTraspasos(nuevasJornadas);
        if (adapter != null) {
            adapter.actualizarTraspasos(traspasos);
            actualizarVisibilidad();
        }
    }
} 