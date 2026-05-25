package com.groupeight.credencialmanger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.groupeight.credencialmanger.datos.FireBase;
import com.groupeight.credencialmanger.presentacion.Login;
import com.groupeight.credencialmanger.presentacion.RegistroUsuario;
import com.groupeight.credencialmanger.negocio.KeyStoreManager;

public class MainActivity extends AppCompatActivity {

    KeyStoreManager ks = new KeyStoreManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnIrLogin = findViewById(R.id.btnIrInicio);
        btnIrLogin.setOnClickListener(v -> irLogin());


    }

    public void irRegistro(View view){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

    private void irLogin(){
        startActivity(new Intent(this, Login.class));
    }
}