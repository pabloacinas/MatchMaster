package com.azotec.matchmaster;

import java.io.Serializable;
import java.util.List;

public class LigaCompleta implements Serializable {
    private Liga liga;
    private List<Equipo> equipos;
    private List<Jornada> jornadas;
    private boolean esIdaVuelta;
    
    public LigaCompleta(Liga liga, List<Equipo> equipos, List<Jornada> jornadas, boolean esIdaVuelta) {
        this.liga = liga;
        this.equipos = equipos;
        this.jornadas = jornadas;
        this.esIdaVuelta = esIdaVuelta;
    }
    
    // Getters y Setters
    public Liga getLiga() {
        return liga;
    }
    
    public void setLiga(Liga liga) {
        this.liga = liga;
    }
    
    public List<Equipo> getEquipos() {
        return equipos;
    }
    
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    public List<Jornada> getJornadas() {
        return jornadas;
    }
    
    public void setJornadas(List<Jornada> jornadas) {
        this.jornadas = jornadas;
    }
    
    public boolean isEsIdaVuelta() {
        return esIdaVuelta;
    }
    
    public void setEsIdaVuelta(boolean esIdaVuelta) {
        this.esIdaVuelta = esIdaVuelta;
    }
} 