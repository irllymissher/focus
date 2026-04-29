package com.example.focusflowsinbackend.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.focusflowsinbackend.R;

import java.io.File;


/**
 * Actividad temporal utilizada exclusivamente para probar
 * el flujo de la cámara y la configuración del FileProvider de forma aislada.
 * Esta clase no forma parte de la arquitectura final, sino que sirve como
 * entorno de pruebas seguro antes de integrar el código en la vista principal.
 */
public class CamaraTestActivity extends AppCompatActivity {

    // Identificador único para saber que el resultado que vuelve a la Actividad es el de la cámara
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // Variable global para almacenar la ruta segura temporal donde la cámara guardará el archivo
    private Uri rutaFotoTemporal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_test);

        Button btnCamara = findViewById(R.id.btn_test_camara);

        // Escuchador del botón que lanza el proceso fotográfico
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    /**
     * Prepara el archivo físico en el almacenamiento del dispositivo y lanza
     * la aplicación nativa de la cámara pasándole la ruta de guardado.
     */
    private void abrirCamara() {

        // Accedemos a la carpeta privada de la app (/files/FotosFocusFlow)
        Intent tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File carpeta = new File(getExternalFilesDir(null), "FotosFocusFlow");

        // Si la carpeta no existe, la creamos
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Le decimos al Intent de la cámara dónde guardar la imagen
        File archivoFoto = new File(carpeta, "foto_prueba.jpg");

        // Generamos la URI segura usando el FileProvider
        rutaFotoTemporal = FileProvider.getUriForFile(this, getPackageName() + ".provider", archivoFoto);

        // Le decimos al Intent de la cámara dónde guardar la imagen
        tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, rutaFotoTemporal);

        startActivityForResult(tomarFotoIntent, REQUEST_IMAGE_CAPTURE);
    }

    /**
     * Intercepta la respuesta de las aplicaciones externas (como la cámara)
     * una vez que han terminado su trabajo y devuelven el control a FocusFlow.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Aseguramos que la respuesta sea de nuestra petición de cámara
        // y que el usuario haya tomado la foto con éxito (OK).
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Foto guardada en: " + rutaFotoTemporal.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Foto cancelada", Toast.LENGTH_SHORT).show();
        }
    }
}