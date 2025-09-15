package com.azotec.matchmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class EquipoSpinnerAdapter extends ArrayAdapter<Equipo> {
    
    private Context context;
    private List<Equipo> equipos;
    
    public EquipoSpinnerAdapter(Context context, List<Equipo> equipos) {
        super(context, 0, equipos);
        this.context = context;
        this.equipos = equipos;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return crearVistaPersonalizada(position, convertView, parent);
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return crearVistaPersonalizada(position, convertView, parent);
    }
    
    private View crearVistaPersonalizada(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        
        Equipo equipo = equipos.get(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(equipo.getNombre() + " (" + equipo.getPropietario() + ")");
        
        // Cambiar color según el propietario
        if (equipo.getPropietario().contains("Usuario 1") || equipo.getPropietario().contains("usuario1")) {
            textView.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        } else {
            textView.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }
        
        return convertView;
    }
} 