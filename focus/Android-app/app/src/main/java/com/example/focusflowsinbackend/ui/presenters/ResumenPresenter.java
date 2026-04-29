package com.example.focusflowsinbackend.ui.presenters;

import android.os.Handler;
import android.os.Looper;

import com.example.focusflowsinbackend.ui.interfaces.IResumenView;

import java.util.List;
import java.util.concurrent.Executors;
import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Services.SesionService;

public class ResumenPresenter {
    private final IResumenView vista;
    private final SesionService sesionService;

    public ResumenPresenter(IResumenView vista, SesionService sesionService) {
        this.vista = vista;
        this.sesionService = sesionService;
    }

    public void cargarDatos(String usuarioId, int estresUltimaSesion, int numSesion) {
        vista.mostrarCargando();

        // 1. Calcular datos para el gráfico (Lógica de Alejandro movida aquí)
        int energia = Math.max(0, 100 - estresUltimaSesion * 7);
        int estresPct = Math.min(80, estresUltimaSesion * 8);
        int cansancio = Math.max(0, 100 - energia - estresPct);
        vista.actualizarGrafico(energia, estresPct, cansancio, numSesion);

        // 2. Traer historial real del JAR desde Back4App[cite: 12, 15]
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                List<SesionEstudio> historial = sesionService.obtenerHistorial(usuarioId);
                new Handler(Looper.getMainLooper()).post(() -> {
                    vista.mostrarHistorial(historial);
                });
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    vista.mostrarError("Error al cargar historial: " + e.getMessage());
                });
            }
        });
    }
}