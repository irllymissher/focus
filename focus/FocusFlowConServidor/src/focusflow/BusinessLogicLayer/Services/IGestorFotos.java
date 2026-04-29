package focusflow.BusinessLogicLayer.Services;

/**
 * Funcion que recibe el momento INICIO o DESCANSO
 * y devuelve la ruta de la foto guardada en files
 * @author tomif
 */
public interface IGestorFotos {
    String tomarYGuardarFoto(String momento);
}
