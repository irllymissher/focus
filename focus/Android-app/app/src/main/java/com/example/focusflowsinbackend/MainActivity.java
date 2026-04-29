package com.example.focusflowsinbackend;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Actividad Principal (Vista) - Responsabilidad de UI Dev.
 * Esta clase implementará IFocusView y gestionará el inflado de los Layouts XML.
 * Aquí se instanciará el FocusPresenter inyectando los servicios de FocusFlowApplication
 * y las herramientas de hardware (GestorFotosAndroidImpl y GestorUbicacion).
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla el layout principal de la interfaz de usuario,
        // cargando todos los elementos definidos en el archivo XML de diseño.
        setContentView(R.layout.activity_main);

        // TODO: UI Dev instanciará aquí el FocusPresenter
        // TODO: obteniendo los servicios de la clase Application.
    }

    // TODO: UI Dev deberia sobreescribir el método onActivityResult aquí para avisar al Presentador
    // TODO: cuando la cámara (lanzada por GestorFotosAndroidImpl) haya finalizado con éxito.

}