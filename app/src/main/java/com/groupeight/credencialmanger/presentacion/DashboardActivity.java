package com.groupeight.credencialmanger.presentacion;

import android.content.Intent;
import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.groupeight.credencialmanger.R;

public class DashboardActivity extends AppCompatActivity {

     MaterialCardView btnCuentas, btnTarjetas, btnGeneratedPassword;
     MaterialButton btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        btnCuentas = findViewById(R.id.btnCuentasDashboard);
        btnTarjetas = findViewById(R.id.btnTarjetasDashboard);
        btnGeneratedPassword = findViewById(R.id.btnPasswordsDashboard);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesionDashboard);

        btnCuentas.setOnClickListener(v -> {
            startActivity(new Intent(this, MostrarCredenciales.class));
        });

        btnTarjetas.setOnClickListener(v -> {

        });

        btnGeneratedPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, GenerarPasswordActivity.class));
        });

        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}