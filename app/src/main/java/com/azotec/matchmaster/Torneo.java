package com.azotec.matchmaster;

public class Torneo {
    private String id;
    private String nombre;
    private String descripcion;
    private String tipo; // Eliminación, Liga, etc.
    private int numParticipantes;
    private String fechaCreacion;
    
    public Torneo(String id, String nombre, String descripcion, String tipo, int numParticipantes, String fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.numParticipantes = numParticipantes;
        this.fechaCreacion = fechaCreacion;
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
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getNumParticipantes() {
        return numParticipantes;
    }
    
    public void setNumParticipantes(int numParticipantes) {
        this.numParticipantes = numParticipantes;
    }
    
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
} 