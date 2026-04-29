package focusflow.PresentationLogicLayer.Views;

import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Models.TipoDescanso;
import java.util.List;

/**
 * Define el "contrato" de la interfaz de usuario. 
 * Gracias a esta interfaz, el Presentador no depende de la consola ni de Android.
 * La Vista solo obedece órdenes de mostrar información.
 */
public interface IFocusView {

    // --- Órdenes de Navegación y Mostrar Pantallas ---
    void mostrarMensaje(String mensaje);
    
    void mostrarPantallaEscaneo(String momento);
    
    void mostrarPantallaCronometro();
    
    void mostrarPantallaEvaluacion();
    
    void mostrarPantallaDescanso(TipoDescanso descanso);
    
    void mostrarResumen(List<SesionEstudio> historial);

}