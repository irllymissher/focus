package com.example.focusflowsinbackend;

import android.os.Handler;
import android.os.Looper;

public class RespiracionPresenter {
    private final IRespiracionView vista;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int ciclos = 0;
    private int cuentaAtras;

    public RespiracionPresenter(IRespiracionView vista) {
        this.vista = vista;
    }

    public void iniciarCiclo() {
        ejecutarInhalacion();
    }

    private void ejecutarInhalacion() {
        cuentaAtras = 4;
        vista.actualizarFase("Inhala", cuentaAtras, 0xFF7B5EEA); // Púrpura[cite: 51]
        vista.animarCirculo(1.6f, 4000); // Expandir
        iniciarContador(7000, this::ejecutarMantenimiento);
    }

    private void ejecutarMantenimiento() {
        cuentaAtras = 7;
        vista.actualizarFase("Mantén", cuentaAtras, 0xFF8A83A8); // Grisáceo[cite: 51]
        iniciarContador(8000, this::ejecutarExhalacion);
    }

    private void ejecutarExhalacion() {
        cuentaAtras = 8;
        vista.actualizarFase("Exhala", cuentaAtras, 0xFF7B5EEA);
        vista.animarCirculo(1.0f, 8000); // Contraer[cite: 51, 58]
        iniciarContador(4000, () -> {
            ciclos++;
            vista.actualizarCiclos(ciclos);
            ejecutarInhalacion();
        });
    }

    private void iniciarContador(long retardoTotal, Runnable siguienteFase) {
        handler.postDelayed(siguienteFase, retardoTotal);
    }

    public void detener() {
        handler.removeCallbacksAndMessages(null);
    }
}