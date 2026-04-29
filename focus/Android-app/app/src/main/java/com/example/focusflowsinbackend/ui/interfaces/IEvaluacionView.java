package com.example.focusflowsinbackend.ui.interfaces;
import focusflow.BusinessLogicLayer.Models.TipoDescanso;

/**
 * Define lo que la pantalla de evaluación debe ser capaz de hacer.
 */
public interface IEvaluacionView {
    void mostrarCargando();
    void ocultarCargando();
    void navegarADescanso(TipoDescanso tipoAsignado);
    void mostrarError(String mensaje);
}
