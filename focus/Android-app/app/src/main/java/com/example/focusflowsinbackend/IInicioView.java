package com.example.focusflowsinbackend;

public interface IInicioView {
    void mostrarBienvenida(String nombre);
    void navegarAEscaneo(String objetivo, int minutos);
    void navegarASesionSinEscaneo(String objetivo, int minutos);
    void mostrarError(String mensaje);
}