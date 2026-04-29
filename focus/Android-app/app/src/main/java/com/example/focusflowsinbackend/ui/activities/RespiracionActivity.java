package com.example.focusflowsinbackend.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflowsinbackend.ui.interfaces.IRespiracionView;
import com.example.focusflowsinbackend.R;
import com.example.focusflowsinbackend.ui.presenters.RespiracionPresenter;
import com.example.focusflowsinbackend.model.SessionData;
import com.example.focusflowsinbackend.views.BreathingCircleView;
import com.google.android.material.button.MaterialButton;

public class RespiracionActivity extends AppCompatActivity implements IRespiracionView {

    private BreathingCircleView vBreathCircle;
    private TextView tvBreathCount, tvBreathPhase, tvCycles;
    private MaterialButton btnAction;
    private RespiracionPresenter presenter;
    private SessionData sessionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respiracion);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        presenter = new RespiracionPresenter(this);

        bindViews();
    }

    private void bindViews() {
        vBreathCircle = findViewById(R.id.v_breath_circle);
        tvBreathCount = findViewById(R.id.tv_breath_count);
        tvBreathPhase = findViewById(R.id.tv_breath_phase);
        tvCycles = findViewById(R.id.tv_cycles);
        btnAction = findViewById(R.id.btn_breath_action);

        btnAction.setOnClickListener(v -> presenter.iniciarCiclo());
    }

    @Override
    public void actualizarFase(String fase, int segundos, int color) {
        tvBreathPhase.setText(fase);
        tvBreathCount.setText(String.valueOf(segundos));
        vBreathCircle.setPhaseColor(color);
    }

    @Override
    public void animarCirculo(float escala, long duracion) {
        vBreathCircle.animateTo(escala, duracion);
    }

    @Override
    public void actualizarCiclos(int total) {
        tvCycles.setVisibility(View.VISIBLE);
        tvCycles.setText("Ciclos: " + total);
    }

    @Override
    public void navegarAResumen() {
        Intent intent = new Intent(this, ResumenActivity.class);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        finish();
    }
}