package com.azotec.matchmaster;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LigaManager {
    private static LigaManager instance;
    private List<LigaCompleta> ligasCompletas;
    private Context context;
    private static final String PREF_NAME = "MatchMasterPrefs";
    private static final String KEY_LIGAS = "ligas_completas";
    private Gson gson;
    
    private LigaManager(Context context) {
        this.context = context.getApplicationContext();
        this.ligasCompletas = new ArrayList<>();
        this.gson = new Gson();
        cargarLigasGuardadas();
    }
    
    public static LigaManager getInstance(Context context) {
        if (instance == null) {
            instance = new LigaManager(context);
        }
        return instance;
    }
    
    public static LigaManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LigaManager debe ser inicializado con un Context primero");
        }
        return instance;
    }
    
    private void cargarLigasGuardadas() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String ligasJson = sharedPreferences.getString(KEY_LIGAS, "[]");
        Type type = new TypeToken<ArrayList<LigaCompleta>>(){}.getType();
        try {
            ligasCompletas = gson.fromJson(ligasJson, type);
            if (ligasCompletas == null) {
                ligasCompletas = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ligasCompletas = new ArrayList<>();
        }
    }
    
    private void guardarLigas() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String ligasJson = gson.toJson(ligasCompletas);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LIGAS, ligasJson);
        editor.apply();
    }
    
    public void agregarLigaCompleta(LigaCompleta ligaCompleta) {
        // Verificar si la liga ya existe
        String idLiga = ligaCompleta.getLiga().getId();
        for (int i = 0; i < ligasCompletas.size(); i++) {
            if (ligasCompletas.get(i).getLiga().getId().equals(idLiga)) {
                // Actualizar la liga existente
                ligasCompletas.set(i, ligaCompleta);
                guardarLigas();
                return;
            }
        }
        // Si no existe, agregar nueva
        ligasCompletas.add(ligaCompleta);
        guardarLigas();
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
        guardarLigas();
    }
    
    public void actualizarLiga(LigaCompleta ligaCompleta) {
        agregarLigaCompleta(ligaCompleta);
    }
} 