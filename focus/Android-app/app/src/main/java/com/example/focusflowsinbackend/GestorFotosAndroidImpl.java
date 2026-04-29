package com.example.focusflowsinbackend;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

import focusflow.BusinessLogicLayer.Services.IGestorFotos;

public class GestorFotosAndroidImpl implements IGestorFotos {

    private Activity actividad;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public GestorFotosAndroidImpl(Activity actividad) {
        this.actividad = actividad;
    }

    @Override
    public String tomarYGuardarFoto(String momentoDeFoto) {
        Intent tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Creamos el archivo
        File carpeta = new File(
                actividad.getExternalFilesDir(null),
                "FotosFocusFlow");
        if (!carpeta.exists()) carpeta.mkdirs();

        File archivoFoto = new File(
                carpeta, "foto_" +
                momentoDeFoto +
                ".jpg");

        Uri rutaFotoTemporal = FileProvider.getUriForFile(
                actividad,
                actividad.getPackageName() + ".provider",
                archivoFoto);
        tomarFotoIntent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                rutaFotoTemporal);

        // Lanzamos la cámara usando la actividad que nos pasaron
        actividad.startActivityForResult(
                tomarFotoIntent,
                REQUEST_IMAGE_CAPTURE);

        // Devolvemos la ruta absoluta donde la cámara va a guardar la foto
        return archivoFoto.getAbsolutePath();
    }
}
