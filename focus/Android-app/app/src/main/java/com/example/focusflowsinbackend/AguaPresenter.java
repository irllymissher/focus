package com.example.focusflowsinbackend;

public class AguaPresenter {
    private final IAguaView vista;
    private int vasosActuales = 3; // Simulación de datos del JAR o SharedPreferences

    public AguaPresenter(IAguaView vista) {
        this.vista = vista;
    }

    public void cargarEstado() {
        vista.actualizarProgreso(vasosActuales, 8);
    }

    public void registrarConsumo() {
        // Aquí se podría llamar a un servicio del JAR para persistir la hidratación
        vista.navegarASnacks();
    }
}
