package com.example.focusflowsinbackend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;

public class AguaActivity extends AppCompatActivity implements IAguaView {

    private ProgressBar pbWater;
    private TextView tvWaterCount;
    private MaterialButton btnWaterDone;
    private AguaPresenter presenter;
    private SessionData sessionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agua);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        if (sessionData == null) sessionData = new SessionData();

        presenter = new AguaPresenter(this);
        bindViews();
        presenter.cargarEstado();
    }

    private void bindViews() {
        pbWater = findViewById(R.id.pb_water);
        tvWaterCount = findViewById(R.id.tv_water_count);
        btnWaterDone = findViewById(R.id.btn_water_done);
        btnWaterDone.setOnClickListener(v -> presenter.registrarConsumo());
    }

    @Override
    public void actualizarProgreso(int actual, int meta) {
        pbWater.setMax(meta);
        pbWater.setProgress(actual);
        tvWaterCount.setText(actual + " / " + meta + " vasos hoy");
    }

    @Override
    public void navegarASnacks() {
        Intent intent = new Intent(this, SnacksActivity.class);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        finish();
    }
}