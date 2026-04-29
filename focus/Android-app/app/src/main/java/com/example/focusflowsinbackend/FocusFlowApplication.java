package com.example.focusflowsinbackend;
import android.app.Application;

import focusflow.BusinessLogicLayer.Services.SesionService;
import focusflow.BusinessLogicLayer.Services.UsuarioService;
import focusflow.DataAccessLayer.SesionDAOCollectionsImpl;
import focusflow.DataAccessLayer.UsuarioDAOCollectionsImpl;
import focusflow.DataAccessLayer.DescansoDAOBack4AppImpl;

/**
 * Clase global de la aplicación (Composition Root).
 * Se ejecuta una única vez al arrancar la app, antes de que se infle cualquier Activity.
 * Su responsabilidad es instanciar las dependencias pesadas (Servicios y DAOs del .jar)
 * para mantenerlas vivas en memoria durante todo el ciclo de vida de la aplicación.
 */
public class FocusFlowApplication extends Application {

    // Almacenamos la lógica de negocio.
    private SesionService sesionService;
    private UsuarioService usuarioService;

    public void onCreate() {
        super.onCreate();

        // Instacias de bases de datos local
        UsuarioDAOCollectionsImpl usuarioDAO = new UsuarioDAOCollectionsImpl();
        SesionDAOCollectionsImpl sesionDAO = new SesionDAOCollectionsImpl();

        // DAO descansso se mantiene en la nube
        DescansoDAOBack4AppImpl descansoDAO = new DescansoDAOBack4AppImpl();

        // Inyectamos los DAO en los servicios (Inyeccion de dependencias)
        this.usuarioService = new UsuarioService(usuarioDAO);
        this.sesionService = new SesionService(sesionDAO, descansoDAO);
    }

    // Proveen acceso a los servicios para que las
    // Vistas (Activities) puedan instanciar al Presentador
    public SesionService getSesionService() { return sesionService; }
    public UsuarioService getUsuarioService() { return usuarioService; }
}
