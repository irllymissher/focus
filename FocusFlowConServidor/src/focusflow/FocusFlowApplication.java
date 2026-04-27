package focusflow;

import focusflow.BusinessLogicLayer.Services.SesionService;
import focusflow.BusinessLogicLayer.Services.UsuarioService;

import focusflow.DataAccessLayer.DescansoDAOBack4AppImpl;
import focusflow.DataAccessLayer.SesionDAOBack4AppImpl;
import focusflow.DataAccessLayer.UsuarioDAOBack4AppImpl;

import focusflow.PresentationLogicLayer.Presenters.FocusPresenter;
import focusflow.PresentationLogicLayer.Views.IFocusView;
import focusflow.PresentationLogicLayer.Views.VistaConsolaImpl;

public class FocusFlowApplication {

    public static void main(String[] args) {
        System.out.println("Arrancando el sistema FocusFlow (Modo servidor)...\n");
        
        IFocusView vistaConsola = new VistaConsolaImpl();
        
        UsuarioDAOBack4AppImpl usuarioDAO = new UsuarioDAOBack4AppImpl();
        SesionDAOBack4AppImpl sesionDAO = new SesionDAOBack4AppImpl();
        
        // --- NUEVO ---
        DescansoDAOBack4AppImpl descansoDAO = new DescansoDAOBack4AppImpl();
        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        
        // --- ACTUALIZADO ---
        SesionService sesionService = new SesionService(sesionDAO, descansoDAO);
        FocusPresenter presentador = new FocusPresenter(vistaConsola, sesionService, usuarioService);
        presentador.iniciarFocusFlow();
    }
}