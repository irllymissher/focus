package com.example.focusflowsinbackend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.focusflowsinbackend.adapter.SessionAdapter; // Asegúrate de actualizar este Adapter
import com.example.focusflowsinbackend.model.SessionData;
import com.example.focusflowsinbackend.views.PieChartView;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import focusflow.BusinessLogicLayer.Models.SesionEstudio;

public class ResumenActivity extends AppCompatActivity implements IResumenView {

    private PieChartView pieChart;
    private TextView tvEnergyPct, tvStressPct, tvTirednessPct;
    private RecyclerView rvSessions;
    private MaterialButton btnBack;
    private ResumenPresenter presenter;
    private SessionData sessionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);
        if (sessionData == null) sessionData = new SessionData();

        FocusFlowApplication app = (FocusFlowApplication) getApplication();
        presenter = new ResumenPresenter(this, app.getSesionService());

        bindViews();

        // Cargamos datos reales del JAR[cite: 15]
        presenter.cargarDatos("USUARIO_TEST", sessionData.getStressLevel(), sessionData.getSessionNumber());
    }

    private void bindViews() {
        pieChart = findViewById(R.id.pie_chart);
        tvEnergyPct = findViewById(R.id.tv_energy_pct);
        tvStressPct = findViewById(R.id.tv_stress_pct);
        tvTirednessPct = findViewById(R.id.tv_tiredness_pct);
        rvSessions = findViewById(R.id.rv_sessions);
        btnBack = findViewById(R.id.btn_back_to_start);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, InicioActivity.class);
            intent.putExtra("session_number", sessionData.getSessionNumber() + 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void actualizarGrafico(int energia, int estres, int cansancio, int num) {
        tvEnergyPct.setText(energia + "%");
        tvStressPct.setText(estres + "%");
        tvTirednessPct.setText(cansancio + "%");

        float[] values = { energia, estres, cansancio };
        int[] colors = { getColor(R.color.green_primary), getColor(R.color.purple_primary), getColor(R.color.orange_primary) };
        pieChart.setData(values, colors, num); // Usamos tu View personalizada[cite: 60]
    }

    @Override
    public void mostrarHistorial(List<SesionEstudio> sesiones) {
        // NOTA: Deberás ajustar tu SessionAdapter para que acepte List<SesionEstudio> en lugar de SessionData
        SessionAdapter adapter = new SessionAdapter(sesiones);
        rvSessions.setLayoutManager(new LinearLayoutManager(this));
        rvSessions.setAdapter(adapter);
    }

    @Override public void mostrarCargando() { /* Opcional: mostrar un ProgressBar */ }
    @Override public void mostrarError(String m) { Toast.makeText(this, m, Toast.LENGTH_SHORT).show(); }
}