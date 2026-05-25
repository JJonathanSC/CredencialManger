package com.groupeight.credencialmanger.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.CredencialManager;

public class FormularioCredencial extends AppCompatActivity {

    EditText edtUsuario, edtPassword, edtDominio, edtCuenta;
    private String nombreCuenta, packageName, modo, docId;
    MaterialButton btnGuardar;
    private CredencialManager credencialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_credencial);
        credencialManager = new CredencialManager();

        //Recibir los datos de AgregarCredencialActivity

        nombreCuenta = getIntent().getStringExtra("nombreApp");
        packageName = getIntent().getStringExtra("packageName");
        modo = getIntent().getStringExtra("modo");


        edtCuenta = findViewById(R.id.edtCuentaFormCredencial);
        edtUsuario = findViewById(R.id.edtUsuarioFormCredencial);
        edtPassword = findViewById(R.id.edtPassFormCredencial);
        edtDominio = findViewById(R.id.edtDominioFormCredencial);
        edtCuenta.setText(nombreCuenta);

        btnGuardar = findViewById(R.id.btnGuardarFormCredencial);

        if (modo != null){
            docId = getIntent().getStringExtra("docId");
            String usuario = getIntent().getStringExtra("usuario");
            String cuenta = getIntent().getStringExtra("cuenta");
            String password = getIntent().getStringExtra("password");
            String dominio = getIntent().getStringExtra("dominio");

            edtCuenta.setText(cuenta);
            edtUsuario.setText(usuario);
            edtPassword.setText(password);
            edtDominio.setText(dominio);
        }

        btnGuardar.setOnClickListener(v -> guardarCredencial());

    }

    private void guardarCredencial(){
        String cuenta = edtCuenta.getText().toString();
        String usuario = edtUsuario.getText().toString();
        String password = edtPassword.getText().toString();
        String dominio = edtDominio.getText().toString();

        if (modo != null){
            credencialManager.editarCredencial(
                    docId,
                    cuenta,
                    usuario,
                    password,
                    dominio.isEmpty() ? null: dominio,
                    ()-> runOnUiThread(() ->{
                        Toast.makeText(this, "Credencial editada", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MostrarCredenciales.class));
                    }),
                    mensaje -> runOnUiThread(()-> Toast.makeText(this, "Error al editar", Toast.LENGTH_SHORT).show())
            );
            return;
        }
        credencialManager.guardarCredencial(
                cuenta,
                usuario,
                password,
                packageName,
                dominio.isEmpty() ? null: dominio,
                ()-> runOnUiThread(()->{
                    Toast.makeText(this, "Credencial guardada", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MostrarCredenciales.class));
                    //finish();
                }),
                mensaje -> runOnUiThread(() -> Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show())
        );
    }

}