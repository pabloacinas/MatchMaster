package com.azotec.matchmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AgregarEquiposActivity extends AppCompatActivity {
    
    private TextView tvInfoLiga;
    private TextView tvEquiposAgregados;
    private EditText etNombreEquipo;
    private RadioGroup rgPropietario;
    private RadioButton rbUsuario1;
    private RadioButton rbUsuario2;
    private Button btnAgregarEquipo;
    private Button btnFinalizar;
    private RecyclerView rvEquipos;
    
    private String nombreLiga;
    private String descripcionLiga;
    private int numEquipos;
    private String nombreUsuario1;
    private String nombreUsuario2;
    private boolean esIdaVuelta;
    
    private List<Equipo> equipos;
    private EquipoAdapter equipoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipos);
        
        obtenerDatosIntent();
        inicializarVistas();
        inicializarDatos();
        configurarRecyclerView();
        configurarBotones();
        actualizarInfoLiga();
    }
    
    private void obtenerDatosIntent() {
        Intent intent = getIntent();
        nombreLiga = intent.getStringExtra("nombre_liga");
        descripcionLiga = intent.getStringExtra("descripcion_liga");
        numEquipos = intent.getIntExtra("num_equipos", 0);
        nombreUsuario1 = intent.getStringExtra("nombre_usuario1");
        nombreUsuario2 = intent.getStringExtra("nombre_usuario2");
        esIdaVuelta = intent.getBooleanExtra("es_ida_vuelta", false);
    }
    
    private void inicializarVistas() {
        tvInfoLiga = findViewById(R.id.tv_info_liga);
        tvEquiposAgregados = findViewById(R.id.tv_equipos_agregados);
        etNombreEquipo = findViewById(R.id.et_nombre_equipo);
        rgPropietario = findViewById(R.id.rg_propietario);
        rbUsuario1 = findViewById(R.id.rb_usuario1);
        rbUsuario2 = findViewById(R.id.rb_usuario2);
        btnAgregarEquipo = findViewById(R.id.btn_agregar_equipo);
        btnFinalizar = findViewById(R.id.btn_finalizar);
        rvEquipos = findViewById(R.id.rv_equipos);
        
        // Configurar radio buttons
        rbUsuario1.setText(nombreUsuario1);
        rbUsuario2.setText(nombreUsuario2);
        rbUsuario1.setChecked(true);
    }
    
    private void inicializarDatos() {
        equipos = new ArrayList<>();
    }
    
    private void configurarRecyclerView() {
        equipoAdapter = new EquipoAdapter(equipos);
        rvEquipos.setLayoutManager(new LinearLayoutManager(this));
        rvEquipos.setAdapter(equipoAdapter);
    }
    
    private void configurarBotones() {
        btnAgregarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEquipo();
            }
        });
        
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarCreacionLiga();
            }
        });
    }
    
    private void actualizarInfoLiga() {
        String info = "Liga: " + nombreLiga + "\n" +
                     "Equipos: " + equipos.size() + "/" + numEquipos + "\n" +
                     "Formato: " + (esIdaVuelta ? "Ida y Vuelta" : "Solo Ida");
        tvInfoLiga.setText(info);
        
        if (equipos.size() == numEquipos) {
            btnFinalizar.setEnabled(true);
            btnAgregarEquipo.setEnabled(false);
            tvEquiposAgregados.setText("¡Todos los equipos han sido agregados!");
        } else {
            btnFinalizar.setEnabled(false);
            btnAgregarEquipo.setEnabled(true);
            tvEquiposAgregados.setText("Agrega " + (numEquipos - equipos.size()) + " equipos más");
        }
    }
    
    private void agregarEquipo() {
        String nombreEquipo = etNombreEquipo.getText().toString().trim();
        
        if (nombreEquipo.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el nombre del equipo", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Verificar que el nombre no esté duplicado
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreEquipo)) {
                Toast.makeText(this, "Ya existe un equipo con ese nombre", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        
        // Verificar distribución de equipos por usuario
        String propietario = rbUsuario1.isChecked() ? nombreUsuario1 : nombreUsuario2;
        int equiposUsuario1 = 0;
        int equiposUsuario2 = 0;
        
        for (Equipo equipo : equipos) {
            if (equipo.getPropietario().equals(nombreUsuario1)) {
                equiposUsuario1++;
            } else {
                equiposUsuario2++;
            }
        }
        
        if (rbUsuario1.isChecked() && equiposUsuario1 >= numEquipos / 2) {
            Toast.makeText(this, nombreUsuario1 + " ya tiene todos sus equipos asignados", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (rbUsuario2.isChecked() && equiposUsuario2 >= numEquipos / 2) {
            Toast.makeText(this, nombreUsuario2 + " ya tiene todos sus equipos asignados", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Agregar equipo
        Equipo nuevoEquipo = new Equipo(nombreEquipo, propietario);
        equipos.add(nuevoEquipo);
        equipoAdapter.notifyItemInserted(equipos.size() - 1);
        
        // Limpiar campo
        etNombreEquipo.setText("");
        
        // Actualizar información
        actualizarInfoLiga();
        
        Toast.makeText(this, "Equipo agregado: " + nombreEquipo, Toast.LENGTH_SHORT).show();
    }
    
    private void finalizarCreacionLiga() {
        if (equipos.size() != numEquipos) {
            Toast.makeText(this, "Debes agregar todos los equipos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Crear la liga
        Liga liga = new Liga(
            String.valueOf(System.currentTimeMillis()), // ID temporal
            nombreLiga,
            "", // Descripción vacía por defecto
            numEquipos,
            new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date())
        );
        
        // Generar jornadas
        List<Jornada> jornadas = generarJornadas();
        
        // Crear intent para la actividad de la liga
        Intent intent = new Intent(this, LigaDetalleActivity.class);
        intent.putExtra("liga", liga);
        intent.putExtra("equipos", new ArrayList<>(equipos));
        intent.putExtra("jornadas", new ArrayList<>(jornadas));
        intent.putExtra("es_ida_vuelta", esIdaVuelta);
        startActivity(intent);
        finish();
    }
    
    private List<Jornada> generarJornadas() {
        List<Jornada> jornadas = new ArrayList<>();
        int numJornadas = esIdaVuelta ? (numEquipos - 1) * 2 : (numEquipos - 1);
        
        for (int i = 1; i <= numJornadas; i++) {
            Jornada jornada = new Jornada(i, new ArrayList<>());
            jornadas.add(jornada);
        }
        
        // Generar emparejamientos
        generarEmparejamientos(jornadas);
        
        return jornadas;
    }
    
    private void generarEmparejamientos(List<Jornada> jornadas) {
        List<Equipo> equiposUsuario1 = new ArrayList<>();
        List<Equipo> equiposUsuario2 = new ArrayList<>();
        
        // Separar equipos por usuario
        for (Equipo equipo : equipos) {
            if (equipo.getPropietario().equals(nombreUsuario1)) {
                equiposUsuario1.add(equipo);
            } else {
                equiposUsuario2.add(equipo);
            }
        }
        
        int numJornadas = jornadas.size();
        int partidosPorJornada = numEquipos / 2;
        
        for (int jornada = 0; jornada < numJornadas; jornada++) {
            for (int partido = 0; partido < partidosPorJornada; partido++) {
                Equipo equipoLocal = equiposUsuario1.get(partido);
                Equipo equipoVisitante = equiposUsuario2.get(partido);
                
                Partido partidoObj = new Partido(
                    equipoLocal,
                    equipoVisitante,
                    jornada + 1,
                    partido + 1
                );
                
                jornadas.get(jornada).getPartidos().add(partidoObj);
            }
            
            // Rotar equipos para la siguiente jornada
            if (jornada < numJornadas - 1) {
                rotarEquipos(equiposUsuario1, equiposUsuario2);
            }
        }
    }
    
    private void rotarEquipos(List<Equipo> equipos1, List<Equipo> equipos2) {
        // Rotación simple: el primer equipo del usuario 2 va al final
        if (!equipos2.isEmpty()) {
            Equipo primerEquipo = equipos2.remove(0);
            equipos2.add(primerEquipo);
        }
    }
} 