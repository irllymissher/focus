package com.example.focusflowsinbackend;
import android.app.Application;

import focusflow.BusinessLogicLayer.Models.Usuario;
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
    private Usuario usuarioActual; // Guardaremos aquí quién está usando la app

    public void onCreate() {
        super.onCreate();

        // 1. Instancias que NO usan internet al crearse (Seguro en el Hilo Principal)
        UsuarioDAOCollectionsImpl usuarioDAO = new UsuarioDAOCollectionsImpl();
        SesionDAOCollectionsImpl sesionDAO = new SesionDAOCollectionsImpl();
        DescansoDAOBack4AppImpl descansoDAO = new DescansoDAOBack4AppImpl();

        this.usuarioService = new UsuarioService(usuarioDAO);

        // 2. Metemos la inicialización de SesionService (que descarga cosas de Back4App)
        // y la búsqueda del usuario en un hilo secundario para no bloquear la pantalla.
        new Thread(() -> {

            sesionService = new SesionService(sesionDAO, descansoDAO);

            // Buscamos o creamos al usuario
            usuarioActual = usuarioService.buscarUsuario("Tester_Android");
            if (usuarioActual == null) {
                usuarioActual = usuarioService.registrarNuevoUsuario("Tester_Android");
            }
        }).start();
    }

    // Proveen acceso a los servicios para que las
    // Vistas (Activities) puedan instanciar al Presentador
    public SesionService getSesionService() { return sesionService; }
    public UsuarioService getUsuarioService() { return usuarioService; }
}
