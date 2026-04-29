package focusflow.DataAccessLayer;

import focusflow.BusinessLogicLayer.Services.IUsuarioDAO;
import focusflow.BusinessLogicLayer.Models.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDAOCollectionsImpl implements IUsuarioDAO {

    private List<Usuario> baseDatosSimulada;

    public UsuarioDAOCollectionsImpl() {
        this.baseDatosSimulada = new ArrayList<>();
    }

    @Override
    public Usuario getUsuarioPorNombre(String nombre) {
        for (Usuario u : baseDatosSimulada) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        // Si termina el bucle y no está, devolvemos null
        return null;
    }

    @Override
    public String insertarUsuario(Usuario usuario) {

        // Simulo una ID
        String idFalso = UUID.randomUUID().toString().substring(0, 10);
        usuario.setId(idFalso);

        baseDatosSimulada.add(usuario);

        System.out.println("[DAO LOCAL] -> Usuario guardado en Memoria con ID: " + idFalso);

        return idFalso;
    }
}
