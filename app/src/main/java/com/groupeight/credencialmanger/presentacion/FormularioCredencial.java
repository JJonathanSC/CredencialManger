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

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.CredencialManager;

public class FormularioCredencial extends AppCompatActivity {

    EditText edtUsuario, edtPassword, edtDominio, edtCuenta;
    private String nombreCuenta, packageName;
    Button btnGuardar;
    private CredencialManager credencialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_credencial);
        credencialManager = new CredencialManager();

        //Recibir los datos de AgregarCredencialActivity

        nombreCuenta = getIntent().getStringExtra("nombreApp");
        packageName = getIntent().getStringExtra("packageName");

        edtCuenta = findViewById(R.id.edtCuentaFrmCredencial);
        edtUsuario = findViewById(R.id.edtUsuarioFrmCredencial);
        edtPassword = findViewById(R.id.edtPassFrmCredencial);
        edtDominio = findViewById(R.id.edtDominioFrmCredencial);

        if (!nombreCuenta.isEmpty()){
            edtCuenta.setText(nombreCuenta);
        }

        btnGuardar = findViewById(R.id.btnGuardarFrmCredencial);
        btnGuardar.setOnClickListener(v -> guardarCredencial());
    }

    private void guardarCredencial(){
        String usuario = edtUsuario.getText().toString();
        String password = edtPassword.getText().toString();
        String dominio = edtDominio.getText().toString();

        credencialManager.guardarCredencial(
                nombreCuenta,
                usuario,
                password,
                packageName.isEmpty() ? null : packageName,
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