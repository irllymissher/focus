package com.example.focusflowsinbackend.ui.activities;

import android.os.CountDownTimer;

import com.example.focusflowsinbackend.ui.interfaces.ISesionView;

public class SesionPresenter {
    private final ISesionView vista;
    private CountDownTimer timer;

    public SesionPresenter(ISesionView vista) {
        this.vista = vista;
    }

    public void arrancarCronometro(int minutos) {
        timer = new CountDownTimer(minutos * 60 * 1000L, 1000) {
            @Override
            public void onTick(long millis) {
                long m = millis / 60000;
                long s = (millis % 60000) / 1000;
                vista.actualizarCronometro(String.format("%02d:%02d", m, s));
            }

            @Override
            public void onFinish() {
                vista.finalizarSesion();
            }
        }.start();
    }

    public void detener() {
        if (timer != null) timer.cancel();
    }
}