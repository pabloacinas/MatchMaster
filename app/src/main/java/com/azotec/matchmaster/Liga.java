package com.azotec.matchmaster;

import java.io.Serializable;

public class Liga implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private int numEquipos;
    private String fechaCreacion;
    
    public Liga(String id, String nombre, String descripcion, int numEquipos, String fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numEquipos = numEquipos;
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
    
    public int getNumEquipos() {
        return numEquipos;
    }
    
    public void setNumEquipos(int numEquipos) {
        this.numEquipos = numEquipos;
    }
    
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
} 