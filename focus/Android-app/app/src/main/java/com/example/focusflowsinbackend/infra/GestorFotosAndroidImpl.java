package com.example.focusflowsinbackend.infra;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

import focusflow.BusinessLogicLayer.Services.IGestorFotos;

/**
 * Implementación en Android de la interfaz IGestorFotos (Netbeans).
 * Actúa como conexión entre la lógica de negocio y el hardware del dispositivo
 * (Cámara y Sistema de Archivos).
 */
public class GestorFotosAndroidImpl implements IGestorFotos {

    // Es necesario tener el contexto de una Activity
    // para poder llamar a getExternalFilesDir() y startActivityForResult().
    private Activity actividad;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * Constructor que inyecta la Actividad actual.
     * @param actividad La pantalla desde la que se invoca la cámara (ej. MainActivity).
     */
    public GestorFotosAndroidImpl(Activity actividad) {
        this.actividad = actividad;
    }

    /**
     * Crea un archivo seguro mediante FileProvider y lanza la cámara nativa.
     * @param momento Cadena de texto para identificar el contexto (ej. "INICIO", "DESCANSO_2").
     * @return La ruta absoluta del archivo donde la cámara GUARDARÁ la foto posteriormente.
     */
    @Override
    public String tomarYGuardarFoto(String momento) {
        Intent tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Obtenemos acceso al almacenamiento interno privado del dispositivo.
        File carpeta = new File(
                actividad.getExternalFilesDir(null),
                "FotosFocusFlow");

        if (!carpeta.exists())
            carpeta.mkdirs();

        // Generamos el nombre del archivo dinámicamente según el momento de la sesión
        // El nombre del archivo de la foto se genera basándose en el valor de "momentoDeFoto",
        // que se pasa como parámetro al método.
        // Esto permite que las fotos se guarden con nombres distintos dependiendo del
        // contexto (por ejemplo, "INICIO" o "DESCANSO_2").
        File archivoFoto = new File(
                carpeta, "foto_" +
                momento +
                ".jpg");

        // Generacion de URI
        Uri rutaFotoTemporal = FileProvider.getUriForFile(
                actividad,
                actividad.getPackageName() + ".provider",
                archivoFoto);

        // Le indicamos a la cámara dónde debe escribir el archivo final
        // Utilizamos el método putExtra() para pasarle la URI de destino al Intent de la cámara.
        // Esto indica a la cámara el archivo en el que debe guardar la foto una vez tomada.
        tomarFotoIntent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                rutaFotoTemporal);

        // Lanzamos la cámara de forma asíncrona usando la actividad proporcionada
        // Llamamos a startActivityForResult() para iniciar la cámara de forma asíncrona,
        // lo que permite esperar el resultado de la foto tomada.
        // Cuando el usuario haya tomado la foto, la app recibirá el resultado en el método
        // onActivityResult() de la actividad que lanzó la cámara.
        actividad.startActivityForResult(
                tomarFotoIntent,
                REQUEST_IMAGE_CAPTURE);

        // Devolvemos la ruta absoluta donde la cámara va a guardar la foto
        return archivoFoto.getAbsolutePath();
    }
}
