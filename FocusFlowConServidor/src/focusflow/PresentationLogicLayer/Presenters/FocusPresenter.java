package focusflow.PresentationLogicLayer.Presenters;

import focusflow.BusinessLogicLayer.Services.UsuarioService;
import focusflow.BusinessLogicLayer.Services.SesionService;
import focusflow.BusinessLogicLayer.Models.*;
import focusflow.PresentationLogicLayer.Views.*;

/** 
 * Es el cerebro que coordina la aplicación.
 * No contiene lógica de base de datos ni de dibujo de pantallas;
 * simplemente dirige el flujo de datos entre la Vista y los Servicios[cite: 157, 254].
 */

public class FocusPresenter {

    // Aplicamos el desacoplamiento: el presentador no sabe si la vista es 
    // una consola o una app Android, solo sabe que cumple con IFocusView
    private IFocusView vista;
    private SesionService sesionService;
    private UsuarioService usuarioService;

    /**
     * Constructor del presentador.
     * Recibe todas sus herramientas (dependencias) ya listas para usar.
     * @param vista
     * @param sesionService
     * @param usuarioService 
     */
    public FocusPresenter(IFocusView vista, SesionService sesionService, UsuarioService usuarioService) {
        this.vista = vista;
        this.sesionService = sesionService;
        this.usuarioService = usuarioService;
    }
    
    /**
     * Método principal que arranca el flujo de la aplicación.
     * Aquí definimos el "paso a paso" de la experiencia del usuario [cite: 13-35].
     */
    public void iniciarFocusFlow() {

        String nombreUsuario = vista.pedirNombreUsuario();
        Usuario usuario = usuarioService.buscarUsuario(nombreUsuario);

        if (usuario != null) {
            vista.mostrarMensaje("¡Bienvenido de nuevo, " + usuario.getNombre() + "!");
        } else {
            vista.mostrarMensaje("Es tu primera vez. ¡Creando perfil para " + nombreUsuario + "...");
            usuario = usuarioService.registrarNuevoUsuario(nombreUsuario);
        }

        String idUsuarioActual = usuario.getId();

        String objetivoEscrito = vista.pedirObjetivoEstudio();
        vista.simularFoto("INICIO");
 
        boolean haPulsadoParar = vista.confirmarInterrupcion();
        int tiempoSimulado = haPulsadoParar ? 45 : 90;
        int estresUsuario = vista.pedirNivelEstres();
        
        // Procesamiento de la sesión en la capa de negocio
        // Mandamos los datos para que el SesionService elija el descanso ideal.
        SesionEstudio sesionProcesada = sesionService.registrarSesion(
            objetivoEscrito, 
            idUsuarioActual, 
            tiempoSimulado, 
            haPulsadoParar, 
            estresUsuario
        );
        
        vista.mostrarPantallaDescanso(sesionProcesada.getTipoDescanso());
        vista.mostrarResumen(sesionService.obtenerHistorial(idUsuarioActual));
    }
}
