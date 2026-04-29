package com.example.focusflowsinbackend.ui.presenters;

import android.os.Handler;
import android.os.Looper;

import com.example.focusflowsinbackend.ui.interfaces.IEvaluacionView;

import java.util.concurrent.Executors;
import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Services.SesionService;

public class EvaluacionPresenter {
    private final IEvaluacionView vista;
    private final SesionService sesionService;

    public EvaluacionPresenter(IEvaluacionView vista, SesionService sesionService) {
        this.vista = vista;
        this.sesionService = sesionService;
    }

    public void procesarFinDeSesion(String objetivo, String usuarioId, int tiempo, int estres) {
        vista.mostrarCargando();

        // Ejecutamos en segundo plano porque registrarSesion usa la red (Back4App)[cite: 2, 15]
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // TU MOTOR DECIDE AQUÍ
                SesionEstudio resultado = sesionService.registrarSesion(
                        objetivo, usuarioId, tiempo, false, estres);

                // Volvemos al hilo de la interfaz para navegar
                new Handler(Looper.getMainLooper()).post(() -> {
                    vista.ocultarCargando();
                    vista.navegarADescanso(resultado.getTipoDescanso());
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    vista.ocultarCargando();
                    vista.mostrarError("Error al guardar: " + e.getMessage());
                });
            }
        });
    }
}