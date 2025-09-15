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
import java.util.ArrayList;
import java.util.List;

public class CrearLigaActivity extends AppCompatActivity {
    
    private EditText etNombreLiga;
    private EditText etNumEquipos;
    private EditText etNombreUsuario1;
    private EditText etNombreUsuario2;
    private RadioGroup rgFormato;
    private RadioButton rbIda;
    private RadioButton rbIdaVuelta;
    private Button btnSiguiente;
    
    private String nombreUsuario1;
    private String nombreUsuario2;
    private int numEquipos;
    private boolean esIdaVuelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_liga);
        
        inicializarVistas();
        configurarBotones();
    }
    
    private void inicializarVistas() {
        etNombreLiga = findViewById(R.id.et_nombre_liga);
        etNumEquipos = findViewById(R.id.et_num_equipos);
        etNombreUsuario1 = findViewById(R.id.et_nombre_usuario1);
        etNombreUsuario2 = findViewById(R.id.et_nombre_usuario2);
        rgFormato = findViewById(R.id.rg_formato);
        rbIda = findViewById(R.id.rb_ida);
        rbIdaVuelta = findViewById(R.id.rb_ida_vuelta);
        btnSiguiente = findViewById(R.id.btn_siguiente);
        
        // Configurar valores por defecto
        rbIda.setChecked(true);
        esIdaVuelta = false;
    }
    
    private void configurarBotones() {
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDatos()) {
                    guardarDatosLiga();
                    abrirAgregarEquipos();
                }
            }
        });
        
        rgFormato.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_ida) {
                    esIdaVuelta = false;
                } else if (checkedId == R.id.rb_ida_vuelta) {
                    esIdaVuelta = true;
                }
            }
        });
    }
    
    private boolean validarDatos() {
        String nombreLiga = etNombreLiga.getText().toString().trim();
        String numEquiposStr = etNumEquipos.getText().toString().trim();
        String usuario1 = etNombreUsuario1.getText().toString().trim();
        String usuario2 = etNombreUsuario2.getText().toString().trim();
        
        if (nombreLiga.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el nombre de la liga", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (numEquiposStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el número de equipos", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        try {
            numEquipos = Integer.parseInt(numEquiposStr);
            if (numEquipos < 4 || numEquipos > 20) {
                Toast.makeText(this, "El número de equipos debe estar entre 4 y 20", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (numEquipos % 2 != 0) {
                Toast.makeText(this, "El número de equipos debe ser par", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingresa un número válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (usuario1.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el nombre del usuario 1", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (usuario2.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el nombre del usuario 2", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (usuario1.equals(usuario2)) {
            Toast.makeText(this, "Los nombres de usuario deben ser diferentes", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private void guardarDatosLiga() {
        nombreUsuario1 = etNombreUsuario1.getText().toString().trim();
        nombreUsuario2 = etNombreUsuario2.getText().toString().trim();
    }
    
    private void abrirAgregarEquipos() {
        Intent intent = new Intent(this, AgregarEquiposActivity.class);
        intent.putExtra("nombre_liga", etNombreLiga.getText().toString().trim());
        intent.putExtra("num_equipos", numEquipos);
        intent.putExtra("nombre_usuario1", nombreUsuario1);
        intent.putExtra("nombre_usuario2", nombreUsuario2);
        intent.putExtra("es_ida_vuelta", esIdaVuelta);
        startActivity(intent);
        finish();
    }
} 