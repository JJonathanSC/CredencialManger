package com.groupeight.credencialmanger.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.Autenticacion;

public class RegistroUsuario extends AppCompatActivity {
    EditText email, password, masterPassword;
    Autenticacion auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario_activity);

        auth = new Autenticacion();

        email = findViewById(R.id.edtEmailRegistro);
        password = findViewById(R.id.edtPasswordRegistro);
        masterPassword = findViewById(R.id.edtMasterPassRegistro);

        Button btnRegistrarUsuario = findViewById(R.id.btnRegistro);
        btnRegistrarUsuario.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario(){
        String _email = email.getText().toString();
        String _pass = password.getText().toString();
        String _master = masterPassword.getText().toString();
        auth.registrarUsuario(_email, _pass,_master,
                () ->{
                    runOnUiThread(() ->{
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("TAG1", "HA INGRESADO :)");
                        startActivity(new Intent(this, Login.class));
                        finish();
                    });
                },
                mensaje -> {
            runOnUiThread(() -> Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show());
                    Log.d("TAG1", mensaje);
                }
        );
    }

}