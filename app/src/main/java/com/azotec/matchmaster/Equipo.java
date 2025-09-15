package com.azotec.matchmaster;

import java.io.Serializable;

public class Equipo implements Serializable {
    private String id;
    private String nombre;
    private String propietario;
    private int puntos;
    private int partidosJugados;
    private int victorias;
    private int derrotas;
    private int empates;
    private int golesFavor;
    private int golesContra;
    
    public Equipo(String nombre, String propietario) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.nombre = nombre;
        this.propietario = propietario;
        this.puntos = 0;
        this.partidosJugados = 0;
        this.victorias = 0;
        this.derrotas = 0;
        this.empates = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getPropietario() {
        return propietario;
    }
    
    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    public int getPartidosJugados() {
        return partidosJugados;
    }
    
    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }
    
    public int getVictorias() {
        return victorias;
    }
    
    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }
    
    public int getDerrotas() {
        return derrotas;
    }
    
    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }
    
    public int getEmpates() {
        return empates;
    }
    
    public void setEmpates(int empates) {
        this.empates = empates;
    }
    
    public int getGolesFavor() {
        return golesFavor;
    }
    
    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }
    
    public int getGolesContra() {
        return golesContra;
    }
    
    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }
    
    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }
    
    // Métodos para actualizar estadísticas
    public void agregarVictoria(int golesFavor, int golesContra) {
        this.partidosJugados++;
        this.victorias++;
        this.puntos += 3;
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
    }
    
    public void agregarDerrota(int golesFavor, int golesContra) {
        this.partidosJugados++;
        this.derrotas++;
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
    }
    
    public void agregarEmpate(int goles) {
        this.partidosJugados++;
        this.empates++;
        this.puntos += 1;
        this.golesFavor += goles;
        this.golesContra += goles;
    }
} 