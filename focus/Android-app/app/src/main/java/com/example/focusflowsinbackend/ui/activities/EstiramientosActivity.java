package com.example.focusflowsinbackend.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflowsinbackend.R;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;

public class EstiramientosActivity extends AppCompatActivity {

    private static final String[] EMOJIS = { "🧘", "🔄", "🙇", "🦵" };
    private static final String[] NAMES  = { "Cuello", "Hombros", "Espalda", "Piernas" };

    private TextView tvEmoji, tvName;
    private MaterialButton btnNext;
    private int currentStep = 0;
    private SessionData sessionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estiramientos);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);

        tvEmoji = findViewById(R.id.tv_stretch_emoji);
        tvName = findViewById(R.id.tv_stretch_name);
        btnNext = findViewById(R.id.btn_next_stretch);

        renderStep();

        btnNext.setOnClickListener(v -> {
            if (currentStep < NAMES.length - 1) {
                currentStep++;
                renderStep();
            } else {
                Intent intent = new Intent(this, ResumenActivity.class);
                intent.putExtra(SessionData.EXTRA_KEY, sessionData);
                startActivity(intent);
                finish();
            }
        });
    }

    private void renderStep() {
        tvEmoji.setText(EMOJIS[currentStep]);
        tvName.setText(NAMES[currentStep]);
        btnNext.setText(currentStep == NAMES.length -1 ? "FINALIZAR" : "SIGUIENTE");
    }
}