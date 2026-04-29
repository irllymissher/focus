package com.example.focusflowsinbackend.ui.interfaces;

public interface IEscaneoView {
    void actualizarProgreso(int progreso);
    void mostrarResultados(int bpm, String energia, String estres);
    void navegarASesion();
}