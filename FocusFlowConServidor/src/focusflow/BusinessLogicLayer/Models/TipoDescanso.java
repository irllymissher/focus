package focusflow.BusinessLogicLayer.Models;

/**
 * Representa las categorías de descanso que el sistema puede asignar.
 * Estos niveles están directamente relacionados con el nivel de estrés (0-10)
 * y determinan qué "pantalla de descanso" verá el usuario en Android.
 */
public enum TipoDescanso {
    RUNNING,                // Nivel 1
    FOTOGRAFIA,             // Nivel 2
    ESTIRAMIENTO,           // Nivel 3
    SNACK_AGUA,             // Nivel 4
    RESPIRACION             // Nivel 5
}