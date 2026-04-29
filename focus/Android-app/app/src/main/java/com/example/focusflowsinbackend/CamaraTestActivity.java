package com.example.focusflowsinbackend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;

public class CamaraTestActivity extends AppCompatActivity {

    // Código de petición (Request Code) para identificar que el resultado viene de la cámara[cite: 4]
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri rutaFotoTemporal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_test);

        Button btnCamara = findViewById(R.id.btn_test_camara);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    private void abrirCamara() {
        Intent tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Creamos un archivo vacio
        File carpeta = new File(getExternalFilesDir(null), "FotosFocusFlow");

        // Si la carpeta no existe, la creamos
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File archivoFoto = new File(carpeta, "foto_prueba.jpg");

        // Generamos la URI segura usando el FileProvider
        rutaFotoTemporal = FileProvider.getUriForFile(this, getPackageName() + ".provider", archivoFoto);

        // Guardamos la foto en la ruta
        tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, rutaFotoTemporal);

        startActivityForResult(tomarFotoIntent, REQUEST_IMAGE_CAPTURE);
    }

    // Capturas el resultado cuando cierra y se vuelva a la anterior pantalla
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "¡Foto guardada en: " + rutaFotoTemporal.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Se canceló la foto", Toast.LENGTH_SHORT).show();
        }
    }
}