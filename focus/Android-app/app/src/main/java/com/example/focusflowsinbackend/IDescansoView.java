package com.example.focusflowsinbackend;

/**
 * Contrato para la pantalla que muestra las opciones de descanso.
 */
public interface IDescansoView {
    void mostrarOpcionRecomendada(int nivel);
    void habilitarBotonComenzar(boolean habilitado);
    void lanzarActividadDescanso(Class<?> actividadDestino);
}