package com.example.focusflowsinbackend.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.focusflowsinbackend.ui.presenters.EscaneoPresenter;
import com.example.focusflowsinbackend.ui.interfaces.IEscaneoView;
import com.example.focusflowsinbackend.R;
import com.example.focusflowsinbackend.model.SessionData;

/**
 * Pantalla de Escaneo (Vista Pasiva).
 * Se limita a ejecutar las animaciones visuales y mostrar los resultados
 * que el Presenter le indica[cite: 31, 45].
 */
public class EscaneoActivity extends AppCompatActivity implements IEscaneoView {

    private View vScanLine;
    private ProgressBar pbScan;
    private TextView tvScanStatus;
    private LinearLayout llMetrics;

    private EscaneoPresenter presenter;
    private SessionData sessionData;
    private ObjectAnimator scanLineAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaneo);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        if (sessionData == null) sessionData = new SessionData();

        presenter = new EscaneoPresenter(this);

        bindViews();
        setupToolbar();
        iniciarAnimacionLinea();

        // El presentador toma el control del tiempo de escaneo
        presenter.iniciarEscaneo();
    }

    private void bindViews() {
        vScanLine = findViewById(R.id.v_scan_line);
        pbScan = findViewById(R.id.pb_scan);
        tvScanStatus = findViewById(R.id.tv_scan_status);
        llMetrics = findViewById(R.id.ll_metrics);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void iniciarAnimacionLinea() {
        // Animación visual de la línea subiendo y bajando
        scanLineAnimator = ObjectAnimator.ofFloat(vScanLine, "translationY",
                0f, getResources().getDisplayMetrics().heightPixels * 0.3f);
        scanLineAnimator.setDuration(1500);
        scanLineAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        scanLineAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        scanLineAnimator.start();
    }

    // --- IMPLEMENTACIÓN DE IESCANEOVIEW ---

    @Override
    public void actualizarProgreso(int progreso) {
        pbScan.setProgress(progreso);
    }

    @Override
    public void mostrarResultados(int bpm, String energia, String estres) {
        if (scanLineAnimator != null) scanLineAnimator.cancel();
        vScanLine.setVisibility(View.GONE);

        // Guardamos los datos simulados en la mochila para que viajen a la sesión
        sessionData.setBpm(bpm);
        sessionData.setScanEnergy(energia);
        sessionData.setScanStress(estres);

        // Actualizamos la UI con los datos que nos dio el Presenter
        tvScanStatus.setText("¡Escaneo Completado!");
        tvScanStatus.setTextColor(getColor(R.color.green_primary));

        ((TextView) findViewById(R.id.tv_bpm)).setText(String.valueOf(bpm));
        ((TextView) findViewById(R.id.tv_energy_scan)).setText(energia);
        ((TextView) findViewById(R.id.tv_stress_scan)).setText(estres);

        llMetrics.setVisibility(View.VISIBLE);
    }

    @Override
    public void navegarASesion() {
        Intent intent = new Intent(this, SesionActivity.class);
        intent.putExtra(SessionData.EXTRA_KEY, sessionData);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scanLineAnimator != null) scanLineAnimator.cancel();
    }
}