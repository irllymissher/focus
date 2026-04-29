package com.example.focusflowsinbackend;

public class SnacksPresenter {
    private final ISnacksView vista;

    public SnacksPresenter(ISnacksView vista) {
        this.vista = vista;
    }

    public void alSeleccionarSnack() {
        vista.habilitarBotonFinalizar(true);
    }

    public void finalizarDescanso() {
        vista.navegarAResumen();
    }
}
