package com.groupeight.credencialmanger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.groupeight.credencialmanger.presentacion.AgregarCredencialActivity;
import com.groupeight.credencialmanger.presentacion.MostrarCredenciales;

public class noHaceNadaSoloRedirige extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_no_hace_nada_solo_redirige);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btnAgregar = findViewById(R.id.btnAgregarFrmTemp);
        Button btnMostrar = findViewById(R.id.btnMostrarFrmTemp);

        btnMostrar.setOnClickListener(v1 ->
                startActivity(new Intent(this, MostrarCredenciales.class)));

        btnAgregar.setOnClickListener(v1 ->
                startActivity(new Intent(this, AgregarCredencialActivity.class)));
    }
}