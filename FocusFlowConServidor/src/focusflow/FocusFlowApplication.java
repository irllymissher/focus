package focusflow;

import focusflow.BusinessLogicLayer.Services.IGestorFotos;
import focusflow.BusinessLogicLayer.Services.SesionService;
import focusflow.BusinessLogicLayer.Services.UsuarioService;

import focusflow.DataAccessLayer.DescansoDAOBack4AppImpl;
import focusflow.DataAccessLayer.SesionDAOBack4AppImpl;
import focusflow.DataAccessLayer.SesionDAOCollectionsImpl;
import focusflow.DataAccessLayer.UsuarioDAOBack4AppImpl;
import focusflow.DataAccessLayer.UsuarioDAOCollectionsImpl;

import focusflow.PresentationLogicLayer.Presenters.FocusPresenter;
import focusflow.PresentationLogicLayer.Views.IFocusView;
import focusflow.PresentationLogicLayer.Views.VistaConsolaImpl;

public class FocusFlowApplication {

    public static void main(String[] args) {
        System.out.println("Arrancando el sistema FocusFlow (Modo servidor)...\n");
        
        IFocusView vistaConsola = new VistaConsolaImpl();       /* Vista */

        UsuarioDAOBack4AppImpl usuarioDAOBackend = new UsuarioDAOBack4AppImpl();
        SesionDAOBack4AppImpl sesionDAOBackend = new SesionDAOBack4AppImpl();
        DescansoDAOBack4AppImpl descansoDAO = new DescansoDAOBack4AppImpl();
        UsuarioDAOCollectionsImpl usuarioDAO = new UsuarioDAOCollectionsImpl();
        SesionDAOCollectionsImpl sesionDAO = new SesionDAOCollectionsImpl();
        
        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        SesionService sesionService = new SesionService(sesionDAO, descansoDAO);
        
        FocusPresenter presentador = new FocusPresenter(
                vistaConsola, 
                sesionService, 
                usuarioService,
                null,   /* ----> Aqui ira la Cámara en Android */
                null);  /* ----> Aqui ira el GPS en Andorid */ 
        presentador.iniciarFocusFlow();
    }
}