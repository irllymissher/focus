package com.example.focusflowsinbackend.ui.interfaces;

public interface IRespiracionView {
    void actualizarFase(String fase, int segundos, int color);
    void animarCirculo(float escala, long duracion);
    void actualizarCiclos(int total);
    void navegarAResumen();
}