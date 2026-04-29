package com.example.focusflowsinbackend;

import android.os.Handler;
import android.os.Looper;

public class EscaneoPresenter {
    private final IEscaneoView vista;

    public EscaneoPresenter(IEscaneoView vista) {
        this.vista = vista;
    }

    public void iniciarEscaneo() {
        // Simulamos el progreso del 0 al 100 en 3 segundos[cite: 45]
        new Thread(() -> {
            for (int i = 0; i <= 100; i += 5) {
                int progreso = i;
                new Handler(Looper.getMainLooper()).post(() -> vista.actualizarProgreso(progreso));
                try { Thread.sleep(150); } catch (InterruptedException e) {}
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                vista.mostrarResultados(72, "Alta", "Bajo");
                new Handler(Looper.getMainLooper()).postDelayed(vista::navegarASesion, 1500);
            });
        }).start();
    }
}