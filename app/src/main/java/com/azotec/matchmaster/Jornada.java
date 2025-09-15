package com.azotec.matchmaster;

import java.io.Serializable;
import java.util.List;

public class Jornada implements Serializable {
    private int numero;
    private List<Partido> partidos;
    
    public Jornada(int numero, List<Partido> partidos) {
        this.numero = numero;
        this.partidos = partidos;
    }
    
    // Getters y Setters
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public List<Partido> getPartidos() {
        return partidos;
    }
    
    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }
    
    // Métodos útiles
    public int getPartidosJugados() {
        int contador = 0;
        for (Partido partido : partidos) {
            if (partido.isJugado()) {
                contador++;
            }
        }
        return contador;
    }
    
    public int getTotalPartidos() {
        return partidos.size();
    }
    
    public boolean isCompletada() {
        return getPartidosJugados() == getTotalPartidos();
    }
    
    public String getTitulo() {
        return "Jornada " + numero;
    }
} 