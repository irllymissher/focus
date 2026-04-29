package focusflow.PresentationLogicLayer.Views;

import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Models.TipoDescanso;
import java.util.List;

/**
 * Define el "contrato" de la interfaz de usuario. 
 * Gracias a esta interfaz, el Presentador no depende de la consola; 
 * solo pide acciones y la Vista se encarga de cómo ejecutarlas físicamente.
 */
public interface IFocusView {

    // --- Entradas de Datos ---
    String pedirObjetivoEstudio();
    String pedirNombreUsuario();
    int pedirNivelEstres();
    boolean confirmarInterrupcion();

    // --- Acciones y Feedback ---
    void simularFoto(String momento);
    void mostrarPantallaDescanso(TipoDescanso descanso);

    // --- Pantallas de Salida ---
    void mostrarResumen(List<SesionEstudio> historial);
    void mostrarMensaje(String mensaje);

}
