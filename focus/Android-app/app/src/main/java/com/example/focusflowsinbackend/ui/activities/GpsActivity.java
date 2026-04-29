package com.example.focusflowsinbackend.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflowsinbackend.R;
import com.example.focusflowsinbackend.model.SessionData;
import com.google.android.material.button.MaterialButton;

public class GpsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        SessionData sessionData = getIntent().getParcelableExtra(SessionData.EXTRA_KEY);

        MaterialButton btnAction = findViewById(R.id.btn_gps_action);
        btnAction.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResumenActivity.class);
            intent.putExtra(SessionData.EXTRA_KEY, sessionData);
            startActivity(intent);
            finish();
        });
    }
}