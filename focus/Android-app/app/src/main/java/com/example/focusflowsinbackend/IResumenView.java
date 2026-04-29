package com.example.focusflowsinbackend;

import java.util.List;
import focusflow.BusinessLogicLayer.Models.SesionEstudio;

public interface IResumenView {
    void mostrarCargando();
    void mostrarHistorial(List<SesionEstudio> sesiones);
    void actualizarGrafico(int energia, int estres, int cansancio, int numeroSesion);
    void mostrarError(String mensaje);
}