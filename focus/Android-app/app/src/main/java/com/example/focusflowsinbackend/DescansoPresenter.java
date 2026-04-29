package com.example.focusflowsinbackend;

/**
 * Coordina la lógica de selección de descanso.
 */
public class DescansoPresenter {
    private final IDescansoView vista;

    public DescansoPresenter(IDescansoView vista) {
        this.vista = vista;
    }

    public void iniciarConNivelRecomendado(int nivel) {
        vista.mostrarOpcionRecomendada(nivel);
    }

    public void elegirDestino(int nivel) {
        // Quitamos el switch de la Activity y lo traemos aquí
        Class<?> destino;
        switch (nivel) {
            case 1:  destino = GpsActivity.class;            break;
            case 2:  destino = MentalActivity.class;         break;
            case 3:  destino = EstiramientosActivity.class;  break;
            case 4:  destino = AguaActivity.class;           break;
            case 5:  destino = RespiracionActivity.class;    break;
            default: destino = EstiramientosActivity.class;
        }
        vista.lanzarActividadDescanso(destino);
    }
}