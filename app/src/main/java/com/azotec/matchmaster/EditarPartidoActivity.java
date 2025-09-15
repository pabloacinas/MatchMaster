package com.azotec.matchmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class EditarPartidoActivity extends AppCompatActivity {
    
    private TextView tvJornadaInfo;
    private Spinner spinnerEquipoLocal;
    private Spinner spinnerEquipoVisitante;
    private EditText etGolesLocal;
    private EditText etGolesVisitante;
    private EditText etTraspaso;
    private EditText etGoleadoresLocal;
    private EditText etGoleadoresVisitante;
    private Button btnGuardar;
    private Button btnCancelar;
    
    private Partido partido;
    private List<Equipo> equipos;
    private int jornadaNumero;
    private int partidoNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_partido);
        
        obtenerDatosIntent();
        inicializarVistas();
        configurarSpinners();
        cargarDatosPartido();
        configurarBotones();
    }
    
    private void obtenerDatosIntent() {
        Intent intent = getIntent();
        partido = (Partido) intent.getSerializableExtra("partido");
        equipos = (List<Equipo>) intent.getSerializableExtra("equipos");
        jornadaNumero = intent.getIntExtra("jornada_numero", 1);
        partidoNumero = intent.getIntExtra("partido_numero", 1);
    }
    
    private void inicializarVistas() {
        tvJornadaInfo = findViewById(R.id.tv_jornada_info);
        spinnerEquipoLocal = findViewById(R.id.spinner_equipo_local);
        spinnerEquipoVisitante = findViewById(R.id.spinner_equipo_visitante);
        etGolesLocal = findViewById(R.id.et_goles_local);
        etGolesVisitante = findViewById(R.id.et_goles_visitante);
        etTraspaso = findViewById(R.id.et_traspaso);
        etGoleadoresLocal = findViewById(R.id.et_goleadores_local);
        etGoleadoresVisitante = findViewById(R.id.et_goleadores_visitante);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnCancelar = findViewById(R.id.btn_cancelar);
        
        tvJornadaInfo.setText("Jornada " + jornadaNumero + " - Partido " + partidoNumero);
    }
    
    private void configurarSpinners() {
        // Crear adaptadores para los spinners
        EquipoSpinnerAdapter adapterLocal = new EquipoSpinnerAdapter(this, equipos);
        EquipoSpinnerAdapter adapterVisitante = new EquipoSpinnerAdapter(this, equipos);
        
        spinnerEquipoLocal.setAdapter(adapterLocal);
        spinnerEquipoVisitante.setAdapter(adapterVisitante);
        
        // Configurar selección por defecto
        if (equipos != null && !equipos.isEmpty()) {
            spinnerEquipoLocal.setSelection(0);
            if (equipos.size() > 1) {
                spinnerEquipoVisitante.setSelection(1);
            } else {
                spinnerEquipoVisitante.setSelection(0);
            }
        }
    }
    
    private void cargarDatosPartido() {
        if (partido != null && equipos != null && !equipos.isEmpty()) {
            // Seleccionar equipos actuales en los spinners
            int posLocal = encontrarPosicionEquipo(partido.getEquipoLocal());
            int posVisitante = encontrarPosicionEquipo(partido.getEquipoVisitante());
            
            // Si no se encuentra el equipo, usar el primero disponible
            if (posLocal >= 0) {
                spinnerEquipoLocal.setSelection(posLocal);
            } else if (!equipos.isEmpty()) {
                spinnerEquipoLocal.setSelection(0);
            }
            
            if (posVisitante >= 0) {
                spinnerEquipoVisitante.setSelection(posVisitante);
            } else if (equipos.size() > 1) {
                spinnerEquipoVisitante.setSelection(1);
            } else if (!equipos.isEmpty()) {
                spinnerEquipoVisitante.setSelection(0);
            }
            
            // Cargar resultado si ya existe
            if (partido.isJugado()) {
                etGolesLocal.setText(String.valueOf(partido.getGolesLocal()));
                etGolesVisitante.setText(String.valueOf(partido.getGolesVisitante()));
            }
            
            // Cargar traspaso y goleadores si existen
            if (partido.getTraspaso() != null && !partido.getTraspaso().isEmpty()) {
                etTraspaso.setText(partido.getTraspaso());
            }
            if (partido.getGoleadoresLocal() != null && !partido.getGoleadoresLocal().isEmpty()) {
                etGoleadoresLocal.setText(partido.getGoleadoresLocal());
            }
            if (partido.getGoleadoresVisitante() != null && !partido.getGoleadoresVisitante().isEmpty()) {
                etGoleadoresVisitante.setText(partido.getGoleadoresVisitante());
            }
        }
    }
    
    private int encontrarPosicionEquipo(Equipo equipo) {
        if (equipos == null || equipo == null) return -1;
        
        for (int i = 0; i < equipos.size(); i++) {
            Equipo equipoEnLista = equipos.get(i);
            if (equipoEnLista != null && equipoEnLista.getId() != null && 
                equipoEnLista.getId().equals(equipo.getId())) {
                return i;
            }
        }
        return -1;
    }
    
    private void configurarBotones() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPartido();
            }
        });
        
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    private void guardarPartido() {
        // Obtener equipos seleccionados
        Equipo equipoLocal = (Equipo) spinnerEquipoLocal.getSelectedItem();
        Equipo equipoVisitante = (Equipo) spinnerEquipoVisitante.getSelectedItem();
        
        if (equipoLocal == null || equipoVisitante == null) {
            Toast.makeText(this, "Debes seleccionar ambos equipos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (equipoLocal.getId().equals(equipoVisitante.getId())) {
            Toast.makeText(this, "Los equipos no pueden ser iguales", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Obtener goles
        String golesLocalStr = etGolesLocal.getText().toString().trim();
        String golesVisitanteStr = etGolesVisitante.getText().toString().trim();
        
        if (golesLocalStr.isEmpty() || golesVisitanteStr.isEmpty()) {
            Toast.makeText(this, "Debes ingresar los goles de ambos equipos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            int golesLocal = Integer.parseInt(golesLocalStr);
            int golesVisitante = Integer.parseInt(golesVisitanteStr);
            
            if (golesLocal < 0 || golesVisitante < 0) {
                Toast.makeText(this, "Los goles no pueden ser negativos", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Actualizar partido
            partido.setEquipoLocal(equipoLocal);
            partido.setEquipoVisitante(equipoVisitante);
            partido.registrarResultado(golesLocal, golesVisitante);
            
            // Guardar traspaso y goleadores
            String traspaso = etTraspaso.getText().toString().trim();
            String goleadoresLocal = etGoleadoresLocal.getText().toString().trim();
            String goleadoresVisitante = etGoleadoresVisitante.getText().toString().trim();
            
            partido.setTraspaso(traspaso);
            partido.setGoleadoresLocal(goleadoresLocal);
            partido.setGoleadoresVisitante(goleadoresVisitante);
            
            // Devolver resultado
            Intent resultIntent = new Intent();
            resultIntent.putExtra("partido_actualizado", partido);
            resultIntent.putExtra("jornada_numero", jornadaNumero);
            resultIntent.putExtra("partido_numero", partidoNumero);
            setResult(RESULT_OK, resultIntent);
            finish();
            
            Toast.makeText(this, "Partido guardado correctamente", Toast.LENGTH_SHORT).show();
            
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingresa números válidos", Toast.LENGTH_SHORT).show();
        }
    }
} 