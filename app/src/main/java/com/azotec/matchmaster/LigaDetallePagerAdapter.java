package com.azotec.matchmaster;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

public class LigaDetallePagerAdapter extends FragmentStateAdapter {
    
    private List<Equipo> equipos;
    private List<Jornada> jornadas;
    private ClasificacionFragment clasificacionFragment;
    private JornadasFragment jornadasFragment;
    private TraspasosFragment traspasosFragment;
    private GoleadoresFragment goleadoresFragment;
    
    public LigaDetallePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Equipo> equipos, List<Jornada> jornadas) {
        super(fragmentActivity);
        this.equipos = equipos;
        this.jornadas = jornadas;
    }
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                clasificacionFragment = ClasificacionFragment.newInstance(equipos);
                return clasificacionFragment;
            case 1:
                jornadasFragment = JornadasFragment.newInstance(jornadas);
                return jornadasFragment;
            case 2:
                traspasosFragment = TraspasosFragment.newInstance(jornadas);
                return traspasosFragment;
            case 3:
                goleadoresFragment = GoleadoresFragment.newInstance();
                return goleadoresFragment;
            default:
                return null;
        }
    }
    
    @Override
    public int getItemCount() {
        return 4; // 4 pestañas: Clasificación, Jornadas, Traspasos, Goleadores
    }
    
    public void notificarActualizacionClasificacion() {
        if (clasificacionFragment != null) {
            clasificacionFragment.actualizarClasificacion();
        }
    }
    
    public void notificarActualizacionTraspasos() {
        if (traspasosFragment != null) {
            traspasosFragment.actualizarTraspasos(jornadas);
        }
    }
    
    // Los goleadores ahora se gestionan manualmente, no se actualizan automáticamente
} 