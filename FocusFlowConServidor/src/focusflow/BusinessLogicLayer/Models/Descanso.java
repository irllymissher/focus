package focusflow.BusinessLogicLayer.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Esta clase actúa como un contenedor de datos puro
 * dentro de nuestra Arquitectura en Cebolla.
 */
public class Descanso {
    
    /* * NOTA SOBRE GSON:
     * La anotación @SerializedName le dice a la librería GSON el nombre EXACTO 
     * que tiene esta columna en nuestra tabla de Back4App. Esto permite que, al descargar 
     * el JSON de internet, los datos se rellenen automáticamente en nuestras variables de Java.
     */
    @SerializedName("objectId")
    private String id;
    
    @SerializedName("nombre_descanso") 
    private String nombre;

    @SerializedName("duracion_minutos")
    private int duracionMinutos;

    /**
     * Nota: No incluimos Setters porque los tipos de descanso son de "solo lectura" 
     * no creamos ni modificamos descansos desde aquí (eso se hace desde el panel de Back4App).
     * @return 
     */
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getDuracionMinutos() { return duracionMinutos; }
}