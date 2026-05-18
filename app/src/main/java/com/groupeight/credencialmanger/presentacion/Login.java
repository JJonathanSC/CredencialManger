package com.groupeight.credencialmanger.presentacion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.groupeight.credencialmanger.MainActivity;
import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.Autenticacion;
import com.groupeight.credencialmanger.noHaceNadaSoloRedirige;

public class Login extends AppCompatActivity {

    EditText _user, _pass;
    Autenticacion auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        auth = new Autenticacion();

        _user = findViewById(R.id.edtUsuarioLogin);
        _pass = findViewById(R.id.edtPasswordLogin);

        Button btnIniciar= findViewById(R.id.btnLogin);
        btnIniciar.setOnClickListener(v -> iniciarSesion());

    }

    private void iniciarSesion(){
        String usuario = _user.getText().toString();
        String pass = _pass.getText().toString();

        Log.d("TAG3", "Llamando a iniciarSesion");

        auth.iniciarSesion(usuario, pass,
                ()->{
                    runOnUiThread(()->{
                        Log.d("TAG2", "Inicio de sesion exitoso");
                        startActivity(new Intent(this, noHaceNadaSoloRedirige.class));
                        finish();
                    });
                },
                    uid -> {
                    runOnUiThread(() -> mostrarDialogoMasterPass(uid));
                },
                mensaje -> {
                    Toast.makeText(this, "Error: " + mensaje, Toast.LENGTH_SHORT).show();
                });
    }

    private void mostrarDialogoMasterPass(String uid){
        EditText edtMasterPass = new EditText(this);
        edtMasterPass.setHint("Contraseña maestra");

        new AlertDialog.Builder(this)
                .setTitle("Dispositivo nuevo")
                .setMessage("Ingresa tu contraseña maestra para recuperar tus credenciales")
                .setView(edtMasterPass)
                .setPositiveButton("Confirmar", (dialog, which) ->{
                    String _masterPass = edtMasterPass.getText().toString();
                    if (!_masterPass.isEmpty()){
                        restaurarClave(uid, _masterPass);
                    }
                })
                .setNegativeButton("Cancelar",null)
                .show();
    }

    private void restaurarClave(String uid, String masterPass){
        auth.restaurarClaveSecreta(uid, masterPass,
                () ->{
                runOnUiThread(()->{
                    startActivity(new Intent(this, noHaceNadaSoloRedirige.class));
                    });
                },
                mensaje -> {
                    Log.d("TAG2", mensaje);
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                });
    }

}