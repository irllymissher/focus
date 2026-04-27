package focusflow.BusinessLogicLayer.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Representa al estudiante que utiliza la aplicación. 
 * Es la pieza clave para la privacidad, ya que su ID se usa para 
 * filtrar las sesiones en Back4App.
 */
public class Usuario {
    
    @SerializedName("objectId")
    private String id;
    private String nombre; // Nombre del usuario (ej: "Tomas" o "Lucia")

    /**
     * Constructor para crear un usuario nuevo.
     * Al principio solo necesitamos el nombre; el ID lo asignaremos 
     * una vez el servidor nos confirme que se ha guardado correctamente
     */
    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el ID único del usuario. 
     * Importante: Este ID es necesario para realizar las consultas "where" 
     * y descargar solo las sesiones que pertenecen a este usuario
     */
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
