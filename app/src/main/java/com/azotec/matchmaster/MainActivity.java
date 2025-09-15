package com.azotec.matchmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private TextView tvLigasVacio;
    private TextView tvTorneosVacio;
    private RecyclerView rvLigas;
    private RecyclerView rvTorneos;
    private Button btnCrearLiga;
    private Button btnCrearTorneo;
    
    private List<Liga> ligas;
    private List<Torneo> torneos;
    private LigaAdapter ligaAdapter;
    private TorneoAdapter torneoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        inicializarVistas();
        inicializarDatos();
        configurarRecyclerViews();
        configurarBotones();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de ligas cuando se regrese a esta actividad
        ligas = LigaManager.getInstance(this).getLigasGuardadas();
        ligaAdapter.actualizarLigas(ligas);
        actualizarVisibilidadListas();
    }
    
    private void inicializarVistas() {
        tvLigasVacio = findViewById(R.id.tv_ligas_vacio);
        tvTorneosVacio = findViewById(R.id.tv_torneos_vacio);
        rvLigas = findViewById(R.id.rv_ligas);
        rvTorneos = findViewById(R.id.rv_torneos);
        btnCrearLiga = findViewById(R.id.btn_crear_liga);
        btnCrearTorneo = findViewById(R.id.btn_crear_torneo);
    }
    
    private void inicializarDatos() {
        ligas = LigaManager.getInstance(this).getLigasGuardadas();
        torneos = new ArrayList<>();
    }
    
    private void configurarRecyclerViews() {
        // Configurar RecyclerView para ligas
        ligaAdapter = new LigaAdapter(ligas);
        rvLigas.setLayoutManager(new LinearLayoutManager(this));
        rvLigas.setAdapter(ligaAdapter);
        
        ligaAdapter.setOnLigaClickListener(new LigaAdapter.OnLigaClickListener() {
            @Override
            public void onLigaClick(Liga liga) {
                abrirLigaDetalle(liga);
            }
        });
        
        ligaAdapter.setOnLigaEliminarListener(new LigaAdapter.OnLigaEliminarListener() {
            @Override
            public void onLigaEliminar(Liga liga) {
                mostrarDialogoConfirmacionEliminar(liga);
            }
        });
        
        // Configurar RecyclerView para torneos
        torneoAdapter = new TorneoAdapter(torneos);
        rvTorneos.setLayoutManager(new LinearLayoutManager(this));
        rvTorneos.setAdapter(torneoAdapter);
        
        actualizarVisibilidadListas();
    }
    
    private void configurarBotones() {
        btnCrearLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevaLiga();
            }
        });
        
        btnCrearTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNuevoTorneo();
            }
        });
    }
    
    private void actualizarVisibilidadListas() {
        if (ligas.isEmpty()) {
            tvLigasVacio.setVisibility(View.VISIBLE);
            rvLigas.setVisibility(View.GONE);
        } else {
            tvLigasVacio.setVisibility(View.GONE);
            rvLigas.setVisibility(View.VISIBLE);
        }
        
        if (torneos.isEmpty()) {
            tvTorneosVacio.setVisibility(View.VISIBLE);
            rvTorneos.setVisibility(View.GONE);
        } else {
            tvTorneosVacio.setVisibility(View.GONE);
            rvTorneos.setVisibility(View.VISIBLE);
        }
    }
    
    private void crearNuevaLiga() {
        Intent intent = new Intent(this, CrearLigaActivity.class);
        startActivity(intent);
    }
    
    private void crearNuevoTorneo() {
        // TODO: Implementar lógica para crear nuevo torneo
        Toast.makeText(this, "Función de crear torneo en desarrollo", Toast.LENGTH_SHORT).show();
    }
    
    private void abrirLigaDetalle(Liga liga) {
        LigaCompleta ligaCompleta = LigaManager.getInstance(this).getLigaCompletaPorId(liga.getId());
        if (ligaCompleta != null) {
            Intent intent = new Intent(this, LigaDetalleActivity.class);
            intent.putExtra("liga", ligaCompleta.getLiga());
            intent.putExtra("equipos", new ArrayList<>(ligaCompleta.getEquipos()));
            intent.putExtra("jornadas", new ArrayList<>(ligaCompleta.getJornadas()));
            intent.putExtra("es_ida_vuelta", ligaCompleta.isEsIdaVuelta());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error al cargar la liga", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void mostrarDialogoConfirmacionEliminar(Liga liga) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Eliminar Liga")
            .setMessage("¿Estás seguro de que quieres eliminar la liga '" + liga.getNombre() + "'? Esta acción no se puede deshacer.")
            .setPositiveButton("Eliminar", (dialog, which) -> {
                eliminarLiga(liga);
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
    
    private void eliminarLiga(Liga liga) {
        // Eliminar la liga del manager
        LigaManager.getInstance(this).eliminarLiga(liga.getId());
        
        // Actualizar la lista local
        ligas.remove(liga);
        
        // Notificar al adaptador
        ligaAdapter.actualizarLigas(ligas);
        
        // Actualizar visibilidad
        actualizarVisibilidadListas();
        
        Toast.makeText(this, "Liga eliminada: " + liga.getNombre(), Toast.LENGTH_SHORT).show();
    }
} 