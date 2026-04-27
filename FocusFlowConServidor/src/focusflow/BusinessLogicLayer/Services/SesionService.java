package focusflow.BusinessLogicLayer.Services;

import focusflow.BusinessLogicLayer.Models.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Este es el "cerebro" de FocusFlow. Aquí se aplican las reglas de estudio y descanso.
 * Se encarga de decidir qué actividad le toca al usuario según su estrés y de 
 * gestionar el guardado de datos
 */

public class SesionService {

    /* * PRINCIPIO DE INVERSIÓN DE DEPENDENCIAS (DIP):
     * No dependemos de clases de "bajo nivel" (como el DAO de Back4App), sino de interfaces.
     * Esto permite que este servicio funcione igual tanto si guardamos en la nube como 
     * si lo hacemos en la memoria RAM
     */
    private final ISesionDAO sesionDAO;
    private final IDescansoDAO descansoDAO;
    
    /** CACHÉ DINÁMICA DE IDs: 
     * Guardamos los IDs reales de Back4App aquí para no tenerlos escritos "a fuego" (hardcodeadas)
     * Si un ID cambia en el servidor, nuestra app se adapta sola al arrancar
     */
    private final Map<String, String> mapaIdsDescansos;

    /**
     * /**
     * Constructor del servicio.
     * Inyectamos los DAOs necesarios y preparamos la configuración dinámica.
     * @param sesionDAO
     * @param descansoDAO 
     */
    public SesionService(ISesionDAO sesionDAO, IDescansoDAO descansoDAO) {
        this.sesionDAO = sesionDAO;
        this.descansoDAO = descansoDAO;
        this.mapaIdsDescansos = new HashMap<>();
        
        // Al iniciar, descargamos los IDs reales para estar listos.
        cargarDescansosDesdeNube();
    }

    /**
     * Descarga la lista de descansos y mapea sus nombres con 
     * sus IDs de la base de datos.
     */
    private void cargarDescansosDesdeNube() {
        System.out.println("Cargando configuracion de descansos desde Back4App...");
        List<Descanso> descansosNube = descansoDAO.obtenerTodosLosDescansos();
        
        for (Descanso d : descansosNube) {
            // Relacionamos: "RUNNING" -> "LBpJrCtx6z" (ID real en la nube)
            if (d.getNombre() != null) {
                mapaIdsDescansos.put(d.getNombre().toUpperCase(), d.getId());
            }
        }
    }

    /**
     * REGLA DE NEGOCIO
     * Dependiendo del nivel de estrés (0-10) reportado, asignamos una categoría
     * @param estres
     * @return 
     */

    public TipoDescanso calcularDescanso(int estres) {
        if (estres <= 2) return TipoDescanso.RUNNING;
        if (estres <= 4) return TipoDescanso.FOTOGRAFIA;
        if (estres <= 6) return TipoDescanso.ESTIRAMIENTO;
        if (estres <= 8) return TipoDescanso.SNACK_AGUA;
        return TipoDescanso.RESPIRACION;
    }
    
    private String obtenerIdDescanso(TipoDescanso descanso) {
        return mapaIdsDescansos.getOrDefault(descanso.name(), "ID_NO_ENCONTRADO");
    }
    
    
    /**
     * Método principal para registrar el fin de un bloque de estudio.
     * Coordina la creación del objeto, el cálculo del descanso y el guardado.
     * @param objetivo
     * @param usuarioId
     * @param tiempoEstudiado
     * @param interrumpida
     * @param estres
     * @return 
     */
    public SesionEstudio registrarSesion(String objetivo, String usuarioId, int tiempoEstudiado, boolean interrumpida, int estres) {
        
        // Creamos la sesion de estudio
        SesionEstudio nuevaSesion = new SesionEstudio(objetivo);
        nuevaSesion.setUsuarioId(usuarioId);
        nuevaSesion.setTiempoEstudiado(tiempoEstudiado);
        nuevaSesion.setNivelEstres(estres);
        
        // Determinamos el estado final de la sesion
        nuevaSesion.setEstadoSesion(interrumpida ? EstadoSesion.INTERRUMPIDA : EstadoSesion.COMPLETADA);
        
        // Aplicamos la logica para elegir el descanso ideal
        TipoDescanso descanso = calcularDescanso(estres);
        nuevaSesion.setTipoDescanso(descanso);
        
        // Asiganmos su ID
        nuevaSesion.setDescansoId(obtenerIdDescanso(descanso));
        
        // Ordenamos al DAO que guarde la sesion
        // simplmente se encarga de guardarla, no sabe donde ni como.
        sesionDAO.guardarSesion(nuevaSesion);
        return nuevaSesion;
    }
    
    public List<SesionEstudio> obtenerHistorial(String usuarioId) {
        return sesionDAO.listarSesiones(usuarioId);
    }
}