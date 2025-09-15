package com.azotec.matchmaster;

import java.io.Serializable;

public class Partido implements Serializable {
    private String id;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private int jornada;
    private int numeroPartido;
    private boolean jugado;
    private String resultado;
    private String traspaso;
    private String goleadoresLocal;
    private String goleadoresVisitante;
    
    public Partido(Equipo equipoLocal, Equipo equipoVisitante, int jornada, int numeroPartido) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.jornada = jornada;
        this.numeroPartido = numeroPartido;
        this.jugado = false;
        this.resultado = "Pendiente";
        this.traspaso = "";
        this.goleadoresLocal = "";
        this.goleadoresVisitante = "";
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Equipo getEquipoLocal() {
        return equipoLocal;
    }
    
    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }
    
    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }
    
    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }
    
    public int getGolesLocal() {
        return golesLocal;
    }
    
    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }
    
    public int getGolesVisitante() {
        return golesVisitante;
    }
    
    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
    
    public int getJornada() {
        return jornada;
    }
    
    public void setJornada(int jornada) {
        this.jornada = jornada;
    }
    
    public int getNumeroPartido() {
        return numeroPartido;
    }
    
    public void setNumeroPartido(int numeroPartido) {
        this.numeroPartido = numeroPartido;
    }
    
    public boolean isJugado() {
        return jugado;
    }
    
    public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }
    
    public String getResultado() {
        return resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public String getTraspaso() {
        return traspaso;
    }
    
    public void setTraspaso(String traspaso) {
        this.traspaso = traspaso;
    }
    
    public String getGoleadoresLocal() {
        return goleadoresLocal;
    }
    
    public void setGoleadoresLocal(String goleadoresLocal) {
        this.goleadoresLocal = goleadoresLocal;
    }
    
    public String getGoleadoresVisitante() {
        return goleadoresVisitante;
    }
    
    public void setGoleadoresVisitante(String goleadoresVisitante) {
        this.goleadoresVisitante = goleadoresVisitante;
    }
    
    // Métodos para obtener información del partido
    public String getTextoPartido() {
        if (jugado) {
            return equipoLocal.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitante.getNombre();
        } else {
            return equipoLocal.getNombre() + " - - " + equipoVisitante.getNombre();
        }
    }
    
    public void registrarResultado(int golesLocal, int golesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jugado = true;
        
        // Determinar resultado
        if (golesLocal > golesVisitante) {
            this.resultado = "Victoria Local";
            equipoLocal.agregarVictoria(golesLocal, golesVisitante);
            equipoVisitante.agregarDerrota(golesVisitante, golesLocal);
        } else if (golesLocal < golesVisitante) {
            this.resultado = "Victoria Visitante";
            equipoVisitante.agregarVictoria(golesVisitante, golesLocal);
            equipoLocal.agregarDerrota(golesLocal, golesVisitante);
        } else {
            this.resultado = "Empate";
            equipoLocal.agregarEmpate(golesLocal);
            equipoVisitante.agregarEmpate(golesVisitante);
        }
    }
    
    public void resetearResultado() {
        // Resetear estadísticas de los equipos
        // TODO: Implementar lógica para restar estadísticas
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.jugado = false;
        this.resultado = "Pendiente";
    }
} 