package focusflow.BusinessLogicLayer.Services;

import focusflow.BusinessLogicLayer.Models.Descanso;
import java.util.List;

public interface IDescansoDAO {
    List<Descanso> obtenerTodosLosDescansos();
}