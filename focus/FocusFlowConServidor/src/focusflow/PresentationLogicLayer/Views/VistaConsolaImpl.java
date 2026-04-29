package focusflow.PresentationLogicLayer.Views;

import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Models.TipoDescanso;
import java.util.List;
import java.util.Scanner;

/** 
 * Implementación de la interfaz IFocusView para terminales de comandos.
 * Es una "Vista Pasiva": no toma decisiones, solo imprime y lee texto.
 */
public class VistaConsolaImpl implements IFocusView {

    private Scanner scanner;

    public VistaConsolaImpl() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String pedirNombreUsuario() {
        System.out.print("¿Cuál es tu nombre de usuario? ");
        return scanner.nextLine();
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String pedirObjetivoEstudio() {
        System.out.println("\n========================================");
        System.out.println("        FOCUS FLOW - INICIO             ");
        System.out.println("========================================");
        System.out.println("90 Minutos de estudio");
        System.out.print("¿Cual es tu objetivo para esta sesion? \n> ");

        // Programa pausado hasta que se pulse enter
        return scanner.nextLine();
    }

    @Override
    public void simularFoto(String momento) {
        System.out.println("\n[CAMARA] -> Click! Foto de " + momento + " guardada correctamente.");
    }

    @Override
    public boolean confirmarInterrupcion() {
        System.out.println("\n========================================");
        System.out.println("      PANTALLA 2: MODO BURBUJA          ");
        System.out.println("========================================");
        System.out.println("Temporizador en marcha: 90:00 minutos restantes...");
        System.out.println("Modo burbuja: notificaciones desactivadas.");
        System.out.println("Si surge una urgencia, puede interrumpir la sesion.");
        System.out.print("¿Deseas PARAR el temporizador ahora? (S/N): \n> ");

        // quitamos espacio y lo ponemos en mayuscula
        String respuesta = scanner.nextLine().trim().toUpperCase();
        return respuesta.equals("S");
    }

    @Override
    public int pedirNivelEstres() {
        System.out.println("\n========================================");
        System.out.println("      PANTALLA 3: EVALUACION            ");
        System.out.println("========================================");

        int estres = -1;

        while (estres < 0 || estres > 10) {
            System.out.print("¿Cómo de estresado te sientes ahora mismo? (0-10): ");
            try {
                estres = Integer.parseInt(scanner.nextLine());
                if (estres < 0 || estres > 10) {
                    System.out.println("Error: El numero debe estar entre 0 y 10");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un numero valido");
            }
        }
        return estres;
    }

    public void mostrarPantallaDescanso(TipoDescanso descanso) {
        System.out.println("\n========================================");
        System.out.println("      PANTALLA 4: TU DESCANSO           ");
        System.out.println("========================================");
        System.out.println("Su nivel de estres esta siendo analizado...");
        System.out.println("Se ha elegido el mejor descanso para usted: [" + descanso + "]");
        System.out.println("----------------------------------------");

        switch (descanso) {
            case RUNNING:
                System.out.println("Sal a correr 15 min. El GPS te esta trackeando");
                break;
            case FOTOGRAFIA:
                System.out.println("Haz una foto a algun objeto de color azul");
                break;
            case ESTIRAMIENTO:
                System.out.println("Levantate de la silla y estira");
                break;
            case SNACK_AGUA:
                System.out.println("Bebe 2 vasos de agua y come dos snacks");
                break;
            case RESPIRACION:
                System.out.println("Cierra los ojos. Inhala en 4 segundos, exhala en 4 segundos. Repite 5 veces.");
                break;
        }
    }

    @Override
    public void mostrarResumen(List<SesionEstudio> historial) {
        System.out.println("\n========================================");
        System.out.println("      PANTALLA 5: RESUMEN DEL DIA       ");
        System.out.println("========================================");

        if (historial.isEmpty()) {
            System.out.println("Sin sesiones registradas");
        } else {
            // Recorremos la lista que nos ha pasado el Presentador
            for (int i = 0; i < historial.size(); i++) {
                System.out.println("--- Sesion #" + (i + 1) + " ---");
                System.out.println(historial.get(i).toString());
                System.out.println("");
            }
        }
        System.out.println("========================================");
    }

}
