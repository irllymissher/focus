package com.example.focusflowsinbackend.ui.interfaces;

public interface IInicioView {
    void mostrarBienvenida(String nombre);
    void navegarAEscaneo(String objetivo, int minutos);
    void navegarASesionSinEscaneo(String objetivo, int minutos);
    void mostrarError(String mensaje);
}