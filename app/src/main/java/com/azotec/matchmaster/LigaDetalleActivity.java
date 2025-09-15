package com.azotec.matchmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LigaDetalleActivity extends AppCompatActivity {
    
    private TextView tvNombreLiga;
    private TextView tvInfoLiga;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private Button btnGuardarLiga;
    private LigaDetallePagerAdapter pagerAdapter;
    
    private Liga liga;
    private List<Equipo> equipos;
    private List<Jornada> jornadas;
    private boolean esIdaVuelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga_detalle);
        
        obtenerDatosIntent();
        inicializarVistas();
        configurarTabs();
        configurarBotones();
        actualizarInfoLiga();
    }
    
    private void obtenerDatosIntent() {
        Intent intent = getIntent();
        liga = (Liga) intent.getSerializableExtra("liga");
        equipos = (List<Equipo>) intent.getSerializableExtra("equipos");
        jornadas = (List<Jornada>) intent.getSerializableExtra("jornadas");
        esIdaVuelta = intent.getBooleanExtra("es_ida_vuelta", false);
    }
    
    private void inicializarVistas() {
        tvNombreLiga = findViewById(R.id.tv_nombre_liga);
        tvInfoLiga = findViewById(R.id.tv_info_liga);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        btnGuardarLiga = findViewById(R.id.btn_guardar_liga);
    }
    
    private void configurarTabs() {
        pagerAdapter = new LigaDetallePagerAdapter(this, equipos, jornadas);
        viewPager.setAdapter(pagerAdapter);
        
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("CLASIFICACIÓN");
                    break;
                case 1:
                    tab.setText("JORNADAS");
                    break;
                case 2:
                    tab.setText("TRASPASOS");
                    break;
                case 3:
                    tab.setText("GOLEADORES");
                    break;
            }
        }).attach();
    }
    
    private void configurarBotones() {
        btnGuardarLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarLiga();
            }
        });
    }
    
    private void actualizarInfoLiga() {
        tvNombreLiga.setText(liga.getNombre());
        
        String info = "Equipos: " + equipos.size() + " | " +
                     "Formato: " + (esIdaVuelta ? "Ida y Vuelta" : "Solo Ida") + " | " +
                     "Jornadas: " + jornadas.size();
        tvInfoLiga.setText(info);
    }
    
    public List<Equipo> getEquiposOrdenados() {
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
    
    public List<Jornada> getJornadas() {
        return jornadas;
    }
    
    public boolean isEsIdaVuelta() {
        return esIdaVuelta;
    }
    
    public void actualizarLigaEnManager() {
        // Crear liga completa actualizada
        LigaCompleta ligaCompleta = new LigaCompleta(liga, equipos, jornadas, esIdaVuelta);
        
        // Actualizar en el manager
        LigaManager.getInstance(this).agregarLigaCompleta(ligaCompleta);
    }
    
    public void actualizarClasificacion() {
        recalcularEstadisticasEquipos();
        pagerAdapter.notificarActualizacionClasificacion();
        pagerAdapter.notificarActualizacionTraspasos();
        // Los goleadores ahora se gestionan manualmente, no se actualizan automáticamente
    }
    
    private void recalcularEstadisticasEquipos() {
        // Resetear estadísticas de todos los equipos
        for (Equipo equipo : equipos) {
            equipo.setPuntos(0);
            equipo.setPartidosJugados(0);
            equipo.setVictorias(0);
            equipo.setDerrotas(0);
            equipo.setEmpates(0);
            equipo.setGolesFavor(0);
            equipo.setGolesContra(0);
        }
        
        // Recalcular basándose en todos los partidos jugados
        for (Jornada jornada : jornadas) {
            for (Partido partido : jornada.getPartidos()) {
                if (partido.isJugado()) {
                    Equipo equipoLocal = partido.getEquipoLocal();
                    Equipo equipoVisitante = partido.getEquipoVisitante();
                    
                    // Buscar los equipos en la lista actual
                    Equipo equipoLocalActual = encontrarEquipoPorId(equipoLocal.getId());
                    Equipo equipoVisitanteActual = encontrarEquipoPorId(equipoVisitante.getId());
                    
                    if (equipoLocalActual != null && equipoVisitanteActual != null) {
                        int golesLocal = partido.getGolesLocal();
                        int golesVisitante = partido.getGolesVisitante();
                        
                        if (golesLocal > golesVisitante) {
                            equipoLocalActual.agregarVictoria(golesLocal, golesVisitante);
                            equipoVisitanteActual.agregarDerrota(golesVisitante, golesLocal);
                        } else if (golesLocal < golesVisitante) {
                            equipoVisitanteActual.agregarVictoria(golesVisitante, golesLocal);
                            equipoLocalActual.agregarDerrota(golesLocal, golesVisitante);
                        } else {
                            equipoLocalActual.agregarEmpate(golesLocal);
                            equipoVisitanteActual.agregarEmpate(golesVisitante);
                        }
                    }
                }
            }
        }
    }
    
    private Equipo encontrarEquipoPorId(String id) {
        for (Equipo equipo : equipos) {
            if (equipo.getId().equals(id)) {
                return equipo;
            }
        }
        return null;
    }
    
    private void guardarLiga() {
        // Crear liga completa con todos los datos
        LigaCompleta ligaCompleta = new LigaCompleta(liga, equipos, jornadas, esIdaVuelta);
        
        // Agregar la liga completa al manager
        LigaManager.getInstance(this).agregarLigaCompleta(ligaCompleta);
        
        Toast.makeText(this, "Liga guardada correctamente", Toast.LENGTH_SHORT).show();
        
        // Volver a la actividad principal
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
} 