package com.example.focusflowsinbackend.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflowsinbackend.FocusFlowApplication;
import com.example.focusflowsinbackend.ui.interfaces.IInicioView;
import com.example.focusflowsinbackend.ui.interfaces.InicioPresenter;
import com.example.focusflowsinbackend.R;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Pantalla de Inicio (Vista Pasiva).
 * Su única responsabilidad es capturar la configuración de la sesión
 * y delegar el inicio al Presenter[cite: 35, 49].
 */
public class InicioActivity extends AppCompatActivity implements IInicioView {

    private TextInputEditText etGoal;
    private ChipGroup cgDuration;
    private TextView tvTimer;
    private TextView tvSessionBadge;
    private MaterialButton btnStart;
    private MaterialButton btnSkipScan;

    private InicioPresenter presenter;
    private int selectedMinutes = 90; // Valor por defecto del layout[cite: 35]
    private int sessionNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio2); // Mantenemos el diseño visual

        // Recuperar número de sesión (si venimos de un reinicio)[cite: 49, 52]
        sessionNumber = getIntent().getIntExtra("session_number", 1);

        // INYECCIÓN: Obtenemos el servicio de usuario desde la Application
        FocusFlowApplication app = (FocusFlowApplication) getApplication();
        presenter = new InicioPresenter(this, app.getUsuarioService());

        bindViews();
        setupDurationChips();
        setupButtons();

        // Identificamos al usuario en el JAR al arrancar[cite: 5, 20]
        presenter.iniciarApp("Tester_Android");
    }

    private void bindViews() {
        etGoal = findViewById(R.id.et_goal);
        cgDuration = findViewById(R.id.cg_duration);
        tvTimer = findViewById(R.id.tv_timer);
        tvSessionBadge = findViewById(R.id.tv_session_badge);
        btnStart = findViewById(R.id.btn_start);
        btnSkipScan = findViewById(R.id.btn_skip_scan);

        tvSessionBadge.setText("#" + sessionNumber);
    }

    private void setupDurationChips() {
        // Mapeo de IDs de Alejandro a valores numéricos[cite: 35, 49]
        cgDuration.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);

            if (id == R.id.chip_25) selectedMinutes = 25;
            else if (id == R.id.chip_45) selectedMinutes = 45;
            else if (id == R.id.chip_60) selectedMinutes = 60;
            else if (id == R.id.chip_90) selectedMinutes = 90;

            tvTimer.setText(String.format("%02d:00", selectedMinutes));
        });
    }

    private void setupButtons() {
        // Delegamos la validación y decisión al Presenter[cite: 5]
        btnStart.setOnClickListener(v ->
                presenter.validarYComenzar(getObjetivo(), selectedMinutes, true));

        btnSkipScan.setOnClickListener(v ->
                presenter.validarYComenzar(getObjetivo(), selectedMinutes, false));
    }

    private String getObjetivo() {
        return etGoal.getText() != null ? etGoal.getText().toString().trim() : "";
    }

    // --- IMPLEMENTACIÓN DE IInicioView ---

    @Override
    public void mostrarBienvenida(String nombre) {
        // Podrías usar esto para cambiar un TextView de saludo si existiera
        Toast.makeText(this, "Bienvenido, " + nombre, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navegarAEscaneo(String objetivo, int minutos) {
        lanzarSiguientePantalla(EscaneoActivity.class, objetivo, minutos);
    }

    @Override
    public void navegarASesionSinEscaneo(String objetivo, int minutos) {
        lanzarSiguientePantalla(SesionActivity.class, objetivo, minutos);
    }

    private void lanzarSiguientePantalla(Class<?> destino, String objetivo, int minutos) {
        // Creamos la "mochila" de datos DTO[cite: 57]
        SessionData data = new SessionData();
        data.setGoal(objetivo);
        data.setMinutes(minutos);
        data.setSessionNumber(sessionNumber);

        Intent intent = new Intent(this, destino);
        intent.putExtra(SessionData.EXTRA_KEY, data);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void mostrarError(String mensaje) {
        etGoal.setError(mensaje);
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}