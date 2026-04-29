package focusflow.BusinessLogicLayer.Models;

/**
 * Define de forma estricta cómo puede terminar una sesión de estudio.
 * Al usar un Enum en lugar de texto (String), evitamos errores ortográficos
 * que romperían la lógica de los informes o de la base de datos.
 */
public enum EstadoSesion {
    COMPLETADA,
    INTERRUMPIDA
}