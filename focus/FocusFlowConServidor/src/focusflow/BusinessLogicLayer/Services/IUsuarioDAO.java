package focusflow.BusinessLogicLayer.Services;

import focusflow.BusinessLogicLayer.Models.Usuario;

/**
 * Define cómo buscamos y registramos a los estudiantes en el sistema.
 */
public interface IUsuarioDAO {
    
    /**
     * Busca en la base de datos si ya existe un usuario con ese nombre.
     * @param nombre El nombre introducido en el login (ej: "Tomas").
     * @return El objeto Usuario si existe, o null si es la primera vez que entra.
     */
    Usuario getUsuarioPorNombre(String nombre);
    
    /**
     * Registra un nuevo perfil en el sistema.
     * @param usuario El objeto con el nombre del nuevo estudiante.
     * @return El ID (objectId) generado por el servidor tras la inserción.
     */
    String insertarUsuario(Usuario usuario);
}