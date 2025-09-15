package com.azotec.matchmaster;

import java.util.ArrayList;
import java.util.List;

public class LigaManager {
    private static LigaManager instance;
    private List<LigaCompleta> ligasCompletas;
    
    private LigaManager() {
        ligasCompletas = new ArrayList<>();
    }
    
    public static LigaManager getInstance() {
        if (instance == null) {
            instance = new LigaManager();
        }
        return instance;
    }
    
    public void agregarLigaCompleta(LigaCompleta ligaCompleta) {
        // Verificar si la liga ya existe
        String idLiga = ligaCompleta.getLiga().getId();
        for (int i = 0; i < ligasCompletas.size(); i++) {
            if (ligasCompletas.get(i).getLiga().getId().equals(idLiga)) {
                // Actualizar la liga existente
                ligasCompletas.set(i, ligaCompleta);
                return;
            }
        }
        // Si no existe, agregar nueva
        ligasCompletas.add(ligaCompleta);
    }
    
    public List<Liga> getLigasGuardadas() {
        List<Liga> ligas = new ArrayList<>();
        for (LigaCompleta ligaCompleta : ligasCompletas) {
            ligas.add(ligaCompleta.getLiga());
        }
        return ligas;
    }
    
    public LigaCompleta getLigaCompletaPorId(String id) {
        for (LigaCompleta ligaCompleta : ligasCompletas) {
            if (ligaCompleta.getLiga().getId().equals(id)) {
                return ligaCompleta;
            }
        }
        return null;
    }
    
    public void eliminarLiga(String id) {
        ligasCompletas.removeIf(ligaCompleta -> ligaCompleta.getLiga().getId().equals(id));
    }
} 