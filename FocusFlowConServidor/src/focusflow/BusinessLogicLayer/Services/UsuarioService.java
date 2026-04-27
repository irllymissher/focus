package focusflow.BusinessLogicLayer.Services;

import focusflow.BusinessLogicLayer.Models.Usuario;

/**
 * Gestiona la lógica de los perfiles de usuario. 
 * Es el puente entre el Presentador y el sistema de datos para todo lo 
 * relacionado con la identidad de los estudiantes. [cite: 157]
 */
public class UsuarioService {

    /* * PRINCIPIO DE INVERSIÓN DE DEPENDENCIAS (DIP):
     * Al igual que en SesionService, no inyectamos una implementación concreta de Back4App, 
     * sino la interfaz IUsuarioDAO. Esto nos permite cambiar el servidor por 
     * una base de datos local simplemente modificando una línea en el Main. 
     */
    private final IUsuarioDAO usuarioDAO;

    /**
     * Constructor del servicio de usuarios.
     * @param usuarioDAO La interfaz que define cómo acceder a los datos de usuario.
     */
    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    /**
     * Busca a un usuario existente por su nombre. 
     * Es lo primero que hace FocusFlow al arrancar para decidir si dar la 
     * bienvenida o crear un perfil nuevo.
     * @param nombre El nombre introducido en consola (ej: "Tomas").
     * @return El objeto Usuario encontrado o null si no existe en la nube.
     */
    public Usuario buscarUsuario(String nombre) {
        return usuarioDAO.getUsuarioPorNombre(nombre);
    }
    
    /**
     * Crea un perfil nuevo en el sistema.
     * Este paso es crítico porque garantiza que el objeto Usuario que manejamos 
     * en memoria tenga el ID real generado por el servidor (Back4App).
     * @param nombre El nombre del estudiante.
     * @return El objeto Usuario completo (incluyendo su objectId oficial).
     */
    public Usuario registrarNuevoUsuario(String nombre) {
        
        Usuario nuevoUsuario = new Usuario(nombre);
        
        // Lo mandamos a insertar y recuperamos su ID
        String nuevoId = usuarioDAO.insertarUsuario(nuevoUsuario);
        
        // CRITICO -> Asignamos el ID al objeto en memoria
        // Si no hacemos esto no se puede guardar luego la sesion por no saber
        // la ID del dueño. Tendria un ID vacio y las sesiones de estudio se
        // guardan como huerfanas
        nuevoUsuario.setId(nuevoId); 
        return nuevoUsuario;
    }
}