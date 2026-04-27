package focusflow.DataAccessLayer;

import focusflow.BusinessLogicLayer.Services.ISesionDAO;
import java.util.ArrayList;
import java.util.List;
import focusflow.BusinessLogicLayer.Models.SesionEstudio;

public class SesionDAOCollectionsImpl implements ISesionDAO {

    private List<SesionEstudio> baseDatosSimulada;

    public SesionDAOCollectionsImpl() {
        this.baseDatosSimulada = new ArrayList<>();
    } 
    @Override
    public void guardarSesion(SesionEstudio sesion) {
        baseDatosSimulada.add(sesion);
        System.out.println("[DAO LOCAL] -> Sesión guardada correctamente en Base de datos local.");
    }

    @Override
    public List<SesionEstudio> listarSesiones(String usuarioId) {
        List<SesionEstudio> sesionesFiltradas = new ArrayList<>();
        for (SesionEstudio s : baseDatosSimulada) {
            // Si el ID de la sesión coincide con el del usuario actual, lo guardamos
            if (usuarioId.equals(s.getUsuarioId())) {
                sesionesFiltradas.add(s);
            }
        }
        return sesionesFiltradas;
    }
}
