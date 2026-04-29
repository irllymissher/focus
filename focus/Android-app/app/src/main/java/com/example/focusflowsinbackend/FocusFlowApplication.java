package com.example.focusflowsinbackend;
import android.app.Application;

import focusflow.BusinessLogicLayer.Services.SesionService;
import focusflow.BusinessLogicLayer.Services.UsuarioService;
import focusflow.DataAccessLayer.SesionDAOCollectionsImpl;
import focusflow.DataAccessLayer.UsuarioDAOCollectionsImpl;
import focusflow.DataAccessLayer.DescansoDAOBack4AppImpl;

public class FocusFlowApplication extends Application {
    private SesionService sesionService;
    private UsuarioService usuarioService;

    public void onCreate() {
        super.onCreate();

        // Instacias de bases de datos local
        UsuarioDAOCollectionsImpl usuarioDAO = new UsuarioDAOCollectionsImpl();
        SesionDAOCollectionsImpl sesionDAO = new SesionDAOCollectionsImpl();
        DescansoDAOBack4AppImpl descansoDAO = new DescansoDAOBack4AppImpl(); // Este lo dejamos en nube si quieres

        // Se crean los servicios
        this.usuarioService = new UsuarioService(usuarioDAO);
        this.sesionService = new SesionService(sesionDAO, descansoDAO);
    }

    // Getters para que Views puedan utilizarlos
    public SesionService getSesionService() { return sesionService; }
    public UsuarioService getUsuarioService() { return usuarioService; }
}
