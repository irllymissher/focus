package focusflow.BusinessLogicLayer.Models;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;

/**
 * Es el objeto central de la app. Guarda qué ha hecho el usuario, cuánto ha durado 
 * y qué descanso se le ha asignado según su estrés
 */
public class SesionEstudio {

    // --- Atributos ---
    @SerializedName("objectId")
    private String id;
    
    @SerializedName("objetivo")
    private String objetivo;

    @SerializedName("tiempoEstudiado")
    private int tiempoEstudiado;

    @SerializedName("nivelEstres")
    private int nivelEstres;
    
    @SerializedName("estadoSesion")
    private EstadoSesion estadoSesion;

    private TipoDescanso tipoDescanso;

    @SerializedName("usuarioId")
    private String usuarioId;

    @SerializedName("descanso_id")
    private String descansoId;

    /**
     * Constructor para nuevas sesiones. 
     * Generamos un ID aleatorio localmente, aunque Back4App lo reemplazará 
     * por el suyo propio al guardarlo en la nube.
     */
    public SesionEstudio(String objetivo) {
        this.id = UUID.randomUUID().toString();
        this.objetivo = objetivo;
    }

    /**
     *  Usamos getter para que el Presentaor pueda leer datos
     * @return 
     */
    public String getId() {
        return id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public int getTiempoEstudiado() {
        return tiempoEstudiado;
    }

    public int getNivelEstres() {
        return nivelEstres;
    }

    public TipoDescanso getTipoDescanso() {
        return tipoDescanso;
    }

    public EstadoSesion getEstadoSesion() {
        return estadoSesion;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getDescansoId() {
        return descansoId;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Usamos Setters para que el Servicio pueda rellenarlos tras
     * procesar la sesión
     * @param objetivo 
     */
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setTiempoEstudiado(int tiempoEstudiado) {
        this.tiempoEstudiado = tiempoEstudiado;
    }

    public void setNivelEstres(int nivelEstres) {
        this.nivelEstres = nivelEstres;
    }

    public void setEstado(EstadoSesion estado) {
        this.estadoSesion = estado;
    }

    public void setTipoDescanso(TipoDescanso tipoDescanso) {
        this.tipoDescanso = tipoDescanso;
    }

    public void setDescansoId(String descansoId) {
        this.descansoId = descansoId;
    }

    public void setEstadoSesion(EstadoSesion estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Método toString() personalizado para mostrar el resumen en consola.
     * Incluye un "Traductor" manual de IDs a nombres legibles para que el 
     * historial se vea bien sin tener que hacer otra consulta a la nube.
     */
    @Override
    public String toString() {
        String idSeguro = (id == null) ? "SIN-ID" : id.substring(0, Math.min(id.length(), 8));
        String nombreDescansoTexto = "Sin asignar";
        if (descansoId != null) {
            switch (descansoId) {
                case "LBpJrCtx6z":
                    nombreDescansoTexto = "RUNNING";
                    break;
                case "RiLYvRzHrq":
                    nombreDescansoTexto = "FOTOGRAFIA";
                    break;
                case "kPAl5KFAKg":
                    nombreDescansoTexto = "ESTIRAMIENTO";
                    break;
                case "KvWrKPdQi1":
                    nombreDescansoTexto = "SNACK_AGUA";
                    break;
                case "z8SkIzdNeL":
                    nombreDescansoTexto = "RESPIRACION";
                    break;
            }
        }

        return "=== SESIÓN [" + idSeguro + "...] ===\n"
                + "Objetivo: " + objetivo + "\n"
                + "Tiempo: " + tiempoEstudiado + " min (" + estadoSesion + ")\n"
                + "Estrés: " + nivelEstres + "/10\n"
                + "Descanso asignado: " + nombreDescansoTexto + "\n"
                + "==================================";
    }
}
