package com.azotec.matchmaster;

import java.io.Serializable;

public class Traspaso implements Serializable {
    private int jornada;
    private String texto;
    
    public Traspaso(int jornada, String texto) {
        this.jornada = jornada;
        this.texto = texto;
    }
    
    // Getters y Setters
    public int getJornada() {
        return jornada;
    }
    
    public void setJornada(int jornada) {
        this.jornada = jornada;
    }
    
    public String getTexto() {
        return texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getJornadaTexto() {
        return "Jornada " + jornada;
    }
} 