package focusflow.BusinessLogicLayer.Services;

import java.util.List;
import focusflow.BusinessLogicLayer.Models.SesionEstudio;

/**
 * Define las operaciones permitidas para gestionar las sesiones de estudio.
 * Aplicamos el Principio de Inversión de Dependencias (DIP): el Servicio pide 
 * "qué" quiere hacer, y el DAO (en la capa de Acceso a Datos) decide "cómo" hacerlo.
 */
public interface ISesionDAO {

    /**
     * Guarda una sesión completa en el sistema de almacenamiento.
     * @param sesion El objeto con toda la info del estudio y el descanso.
     */
    void guardarSesion(SesionEstudio sesion);             // Create (POST)
    
    /**
     * Recupera todas las sesiones que pertenecen a un usuario concreto. 
     * El usuarioId no podra ver las seisones de otros usuarios. 
     * @param usuarioId El ID único del estudiante (objectId de Back4App).
     * @return Una lista de sesiones para mostrar en el historial.
     */
    List<SesionEstudio> listarSesiones(String usuarioId); 

}
