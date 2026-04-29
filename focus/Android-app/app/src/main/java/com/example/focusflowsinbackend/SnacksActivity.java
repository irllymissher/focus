package com.example.focusflowsinbackend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class SnacksActivity extends AppCompatActivity implements ISnacksView, View.OnClickListener {

    private MaterialButton btnDone;
    private SnacksPresenter presenter;
    private SessionData sessionData;
    private MaterialCardView selectedCleanup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        presenter = new SnacksPresenter(this);

        btnDone = findViewById(R.id.btn_snacks_done);
        btnDone.setOnClickListener(v -> presenter.finalizarDescanso());

        // Configurar clics en las tarjetas de Alejandro
        findViewById(R.id.card_nuts).setOnClickListener(this);
        findViewById(R.id.card_fruit).setOnClickListener(this);
        findViewById(R.id.card_yogurt).setOnClickListener(this);
        findViewById(R.id.card_oats).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (selectedCleanup != null) {
            selectedCleanup.setStrokeWidth(0); // Desmarcar previa
        }
        selectedCleanup = (MaterialCardView) v;
        selectedCleanup.setStrokeWidth(4); // Marcar nueva visualmente
        presenter.alSeleccionarSnack();
    }

    @Override
    public void habilitarBotonFinalizar(boolean habilitado) {
        btnDone.setEnabled(habilitado);
    }

    @Override
    public void navegarAResumen() {
        Intent intent = new Intent(this, ResumenActivity.class);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        finish();
    }
}