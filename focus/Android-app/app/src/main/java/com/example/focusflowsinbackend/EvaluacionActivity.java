package com.example.focusflowsinbackend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;
import focusflow.BusinessLogicLayer.Models.TipoDescanso;

public class EvaluacionActivity extends AppCompatActivity implements IEvaluacionView {

    private SessionData sessionData;
    private EvaluacionPresenter presenter;
    private MaterialButton btnCalculate;
    private int currentStress = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion); // Mantenemos su diseño visual[cite: 33]

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        if (sessionData == null) sessionData = new SessionData();

        // INYECCIÓN: Conectamos con el motor del JAR[cite: 23]
        FocusFlowApplication app = (FocusFlowApplication) getApplication();
        presenter = new EvaluacionPresenter(this, app.getSesionService());

        setupViews();
    }

    private void setupViews() {
        btnCalculate = findViewById(R.id.btn_calculate);
        SeekBar seekBar = findViewById(R.id.seekbar_stress);

        // El botón ya no calcula, solo avisa al presentador
        btnCalculate.setOnClickListener(v -> {
            presenter.procesarFinDeSesion(
                    sessionData.getGoal(),
                    "USUARIO_TEST", // ID temporal[cite: 20]
                    sessionData.getMinutes(),
                    currentStress
            );
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar sb, int p, boolean f) { currentStress = p; }
            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {}
        });
    }

    // --- IMPLEMENTACIÓN DE LA VISTA PASIVA ---

    @Override
    public void mostrarCargando() {
        btnCalculate.setEnabled(false);
        btnCalculate.setText("Sincronizando con la nube...");
    }

    @Override
    public void ocultarCargando() {
        btnCalculate.setEnabled(true);
        btnCalculate.setText("CALCULAR DESCANSO");
    }

    @Override
    public void navegarADescanso(TipoDescanso tipo) {
        // TRADUCTOR: De tu Enum (JAR) al nivel visual de Alejandro (Android)[cite: 16, 44]
        int nivel = 3;
        if (tipo == TipoDescanso.RUNNING) nivel = 1;
        else if (tipo == TipoDescanso.FOTOGRAFIA) nivel = 2;
        else if (tipo == TipoDescanso.ESTIRAMIENTO) nivel = 3;
        else if (tipo == TipoDescanso.SNACK_AGUA) nivel = 4;
        else if (tipo == TipoDescanso.RESPIRACION) nivel = 5;

        sessionData.setBreakLevel(nivel); // Actualizamos la mochila temporal[cite: 57]

        Intent intent = new Intent(this, DESCA);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        finish();
    }

    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}