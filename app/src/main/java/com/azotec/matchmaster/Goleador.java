package com.azotec.matchmaster;

import java.io.Serializable;

public class Goleador implements Serializable {
    private String nombre;
    private int goles;
    
    public Goleador(String nombre, int goles) {
        this.nombre = nombre;
        this.goles = goles;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getGoles() {
        return goles;
    }
    
    public void setGoles(int goles) {
        this.goles = goles;
    }
    
    public void agregarGol() {
        this.goles++;
    }
} 