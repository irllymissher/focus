package com.example.focusflowsinbackend;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.focusflowsinbackend.adapter.BreakOptionAdapter;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;

public class DescansoActivity extends AppCompatActivity
        implements IDescansoView, BreakOptionAdapter.OnBreakSelectedListener {

    private SessionData sessionData;
    private DescansoPresenter presenter;
    private MaterialButton btnStartBreak;
    private RecyclerView rvBreakOptions;
    private int selectedLevel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descanso);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        if (sessionData == null) sessionData = new SessionData();

        presenter = new DescansoPresenter(this);

        bindViews();
        setupToolbar();

        // Iniciamos la vista con el nivel que calculó tu JAR en la pantalla anterior[cite: 15, 57]
        presenter.iniciarConNivelRecomendado(sessionData.getBreakLevel());
    }

    private void bindViews() {
        rvBreakOptions = findViewById(R.id.rv_break_options);
        btnStartBreak  = findViewById(R.id.btn_start_break);

        btnStartBreak.setOnClickListener(v -> presenter.elegirDestino(selectedLevel));
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    // --- IMPLEMENTACIÓN DE IDESCANSOVIEW ---

    @Override
    public void mostrarOpcionRecomendada(int nivel) {
        // Usamos el adaptador de Alejandro, pero ahora nosotros le decimos qué marcar
        BreakOptionAdapter adapter = new BreakOptionAdapter(nivel, this);
        rvBreakOptions.setLayoutManager(new LinearLayoutManager(this));
        rvBreakOptions.setAdapter(adapter);
    }

    @Override
    public void habilitarBotonComenzar(boolean habilitado) {
        btnStartBreak.setEnabled(habilitado);
    }

    @Override
    public void lanzarActividadDescanso(Class<?> actividadDestino) {
        sessionData.setBreakLevel(selectedLevel);
        Intent intent = new Intent(this, actividadDestino);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // --- ESCUCHADOR DEL ADAPTADOR ---

    @Override
    public void onBreakSelected(int level) {
        this.selectedLevel = level;
        habilitarBotonComenzar(true);
    }
}