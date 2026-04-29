package com.example.focusflowsinbackend.ui.interfaces;

import focusflow.BusinessLogicLayer.Models.Usuario;
import focusflow.BusinessLogicLayer.Services.UsuarioService;

public class InicioPresenter {
    private final IInicioView vista;
    private final UsuarioService usuarioService;

    public InicioPresenter(IInicioView vista, UsuarioService usuarioService) {
        this.vista = vista;
        this.usuarioService = usuarioService;
    }

    public void iniciarApp(String nombreDummy) {
        // Usamos tu servicio para buscar o registrar al vuelo[cite: 20]
        Usuario u = usuarioService.buscarUsuario(nombreDummy);
        if (u == null) u = usuarioService.registrarNuevoUsuario(nombreDummy);
        vista.mostrarBienvenida(u.getNombre());
    }

    public void validarYComenzar(String objetivo, int minutos, boolean conEscaneo) {
        if (objetivo.isEmpty()) {
            vista.mostrarError("Debes definir un objetivo para concentrarte");
            return;
        }

        if (conEscaneo) {
            vista.navegarAEscaneo(objetivo, minutos);
        } else {
            vista.navegarASesionSinEscaneo(objetivo, minutos);
        }
    }
}