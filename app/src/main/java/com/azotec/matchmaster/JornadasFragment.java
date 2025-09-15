package com.azotec.matchmaster;

import android.content.Intent;
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
import java.util.List;

public class JornadasFragment extends Fragment {
    
    private TextView tvJornadaActual;
    private Button btnJornadaAnterior;
    private Button btnJornadaSiguiente;
    private RecyclerView rvPartidos;
    private PartidosAdapter adapter;
    
    private List<Jornada> jornadas;
    private int jornadaActual = 0;
    
    public static JornadasFragment newInstance(List<Jornada> jornadas) {
        JornadasFragment fragment = new JornadasFragment();
        Bundle args = new Bundle();
        args.putSerializable("jornadas", new ArrayList<>(jornadas));
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jornadas = (List<Jornada>) getArguments().getSerializable("jornadas");
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jornadas, container, false);
        
        tvJornadaActual = view.findViewById(R.id.tv_jornada_actual);
        btnJornadaAnterior = view.findViewById(R.id.btn_jornada_anterior);
        btnJornadaSiguiente = view.findViewById(R.id.btn_jornada_siguiente);
        rvPartidos = view.findViewById(R.id.rv_partidos);
        
        configurarBotones();
        configurarRecyclerView();
        actualizarJornada();
        
        return view;
    }
    
    private void configurarBotones() {
        btnJornadaAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jornadaActual > 0) {
                    jornadaActual--;
                    actualizarJornada();
                }
            }
        });
        
        btnJornadaSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jornadaActual < jornadas.size() - 1) {
                    jornadaActual++;
                    actualizarJornada();
                }
            }
        });
    }
    
    private void configurarRecyclerView() {
        adapter = new PartidosAdapter(new ArrayList<>());
        rvPartidos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPartidos.setAdapter(adapter);
        
        adapter.setOnPartidoClickListener(new PartidosAdapter.OnPartidoClickListener() {
            @Override
            public void onPartidoClick(Partido partido, int position) {
                abrirEditarPartido(partido, position);
            }
        });
    }
    
    private void actualizarJornada() {
        if (jornadas != null && !jornadas.isEmpty()) {
            Jornada jornada = jornadas.get(jornadaActual);
            tvJornadaActual.setText("Jornada " + jornada.getNumero());
            
            adapter.actualizarPartidos(jornada.getPartidos());
            
            // Actualizar estado de botones
            btnJornadaAnterior.setEnabled(jornadaActual > 0);
            btnJornadaSiguiente.setEnabled(jornadaActual < jornadas.size() - 1);
        }
    }
    
    private void abrirEditarPartido(Partido partido, int position) {
        Intent intent = new Intent(getContext(), EditarPartidoActivity.class);
        intent.putExtra("partido", partido);
        intent.putExtra("equipos", new ArrayList<>(getTodosLosEquipos()));
        intent.putExtra("jornada_numero", jornadaActual + 1);
        intent.putExtra("partido_numero", position + 1);
        
        startActivityForResult(intent, 1001);
    }
    
    private List<Equipo> getTodosLosEquipos() {
        List<Equipo> todosEquipos = new ArrayList<>();
        for (Jornada jornada : jornadas) {
            for (Partido partido : jornada.getPartidos()) {
                if (!todosEquipos.contains(partido.getEquipoLocal())) {
                    todosEquipos.add(partido.getEquipoLocal());
                }
                if (!todosEquipos.contains(partido.getEquipoVisitante())) {
                    todosEquipos.add(partido.getEquipoVisitante());
                }
            }
        }
        return todosEquipos;
    }
    
    private List<Equipo> getEquiposUsuario1() {
        // TODO: Implementar obtención de equipos del usuario 1
        return new ArrayList<>();
    }
    
    private List<Equipo> getEquiposUsuario2() {
        // TODO: Implementar obtención de equipos del usuario 2
        return new ArrayList<>();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == getActivity().RESULT_OK) {
            Partido partidoActualizado = (Partido) data.getSerializableExtra("partido_actualizado");
            int jornadaNum = data.getIntExtra("jornada_numero", 1);
            int partidoNum = data.getIntExtra("partido_numero", 1);
            
            if (partidoActualizado != null) {
                // Actualizar el partido en la jornada correcta
                if (jornadaNum - 1 < jornadas.size()) {
                    Jornada jornada = jornadas.get(jornadaNum - 1);
                    if (partidoNum - 1 < jornada.getPartidos().size()) {
                        jornada.getPartidos().set(partidoNum - 1, partidoActualizado);
                        adapter.notifyDataSetChanged();
                        
                        // Actualizar la liga en el manager si es necesario
                        actualizarLigaEnManager();
                        
                        // Notificar al fragment de clasificación para que se actualice
                        notificarActualizacionClasificacion();
                    }
                }
            }
        }
    }
    
    private void notificarActualizacionClasificacion() {
        // Buscar el fragment de clasificación y notificarle que se actualice
        if (getActivity() instanceof LigaDetalleActivity) {
            LigaDetalleActivity actividad = (LigaDetalleActivity) getActivity();
            actividad.actualizarClasificacion();
        }
    }
    
    private void actualizarLigaEnManager() {
        // Obtener la liga actual del manager y actualizarla
        if (getActivity() instanceof LigaDetalleActivity) {
            LigaDetalleActivity actividad = (LigaDetalleActivity) getActivity();
            actividad.actualizarLigaEnManager();
        }
    }
} 