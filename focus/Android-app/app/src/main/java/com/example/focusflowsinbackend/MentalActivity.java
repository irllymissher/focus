package com.example.focusflowsinbackend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;

public class MentalActivity extends AppCompatActivity {

    private SessionData sessionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental);

        sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);

        MaterialButton btnDone = findViewById(R.id.btn_mental_done);
        TextView tvOptionCorrect = findViewById(R.id.tv_option_1); // "204" es la correcta[cite: 50]

        tvOptionCorrect.setOnClickListener(v -> {
            v.setBackgroundColor(getColor(R.color.green_soft));
            btnDone.setEnabled(true);
        });

        btnDone.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResumenActivity.class);
            intent.putExtra(SessionData.EXTRA_KEY, sessionData);
            startActivity(intent);
            finish();
        });
    }
}