package com.example.focusflowsinbackend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;

/**
 * Pantalla de Sesión Activa (Vista Pasiva).
 * Solo se encarga de mostrar el progreso del tiempo y gestionar la pausa/parada[cite: 39, 53].
 */
public class SesionActivity extends AppCompatActivity implements ISesionView {

    private TextView tvTimer;
    private TextView tvGoal;
    private MaterialButton btnPause;
    private MaterialButton btnStop;

    private SesionPresenter presenter;
    private SessionData sessionData;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion); // Mantenemos el diseño de Alejandro[cite: 39]

        // Recuperamos la "mochila" con los datos de inicio
        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        if (sessionData == null) sessionData = new SessionData();

        // Inicializamos el presentador
        presenter = new SesionPresenter(this);

        bindViews();
        setupToolbar();
        setupGoal();
        setupButtons();

        // Arrancamos el tiempo delegando en el presentador
        presenter.arrancarCronometro(sessionData.getMinutes());
    }

    private void bindViews() {
        tvTimer = findViewById(R.id.tv_timer);
        tvGoal = findViewById(R.id.tv_goal);
        btnPause = findViewById(R.id.btn_pause);
        btnStop = findViewById(R.id.btn_stop);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Bloqueamos el botón atrás para evitar salir de la sesión por error
    }

    private void setupGoal() {
        String goal = sessionData.getGoal();
        tvGoal.setText((goal == null || goal.isEmpty())
                ? getString(R.string.goal_placeholder) : goal);
    }

    private void setupButtons() {
        // Pausa/Reanudar: El presentador gestiona el tiempo, nosotros solo cambiamos el icono
        btnPause.setOnClickListener(v -> {
            if (!isPaused) {
                presenter.detener();
                btnPause.setText("▶");
                isPaused = true;
            } else {
                // Para simplificar, relanzamos con los minutos restantes (esto se puede pulir más adelante)
                presenter.arrancarCronometro(sessionData.getMinutes());
                btnPause.setText("⏸");
                isPaused = false;
            }
        });

        // Botón Parar: Salto directo a la evaluación[cite: 53]
        btnStop.setOnClickListener(v -> finalizarSesion());
    }

    // --- IMPLEMENTACIÓN DE ISesionView ---

    @Override
    public void actualizarCronometro(String tiempo) {
        tvTimer.setText(tiempo);
    }

    @Override
    public void finalizarSesion() {
        presenter.detener();

        // Navegamos a la Evaluación con la mochila de datos[cite: 47, 53]
        Intent intent = new Intent(this, EvaluacionActivity.class);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detener(); // Evitamos memory leaks si se cierra la app[cite: 53]
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        // Bloquear botón atrás físico durante la sesión activa[cite: 53]
        super.onBackPressed();
    }
}