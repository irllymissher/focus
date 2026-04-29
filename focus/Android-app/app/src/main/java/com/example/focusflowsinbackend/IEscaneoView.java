package com.example.focusflowsinbackend;

public interface IEscaneoView {
    void actualizarProgreso(int progreso);
    void mostrarResultados(int bpm, String energia, String estres);
    void navegarASesion();
}