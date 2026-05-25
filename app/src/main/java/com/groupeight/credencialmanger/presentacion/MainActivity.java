package com.groupeight.credencialmanger.presentacion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.Autenticacion;

public class MainActivity extends AppCompatActivity {

    Autenticacion auth;

    EditText _user, _password;
    Button btnIngresar;
    TextView tvRegistrarse;


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

        auth = new Autenticacion();

        _user = findViewById(R.id.edtUsuarioMain);
        _password = findViewById(R.id.edtPasswordMain);
        tvRegistrarse = findViewById(R.id.tvRegistrarseMain);
        btnIngresar = findViewById(R.id.btnIngresarMain);

        btnIngresar.setOnClickListener(v -> iniciarSesion());
        tvRegistrarse.setOnClickListener(v -> registrarUsuario());
    }

    private void iniciarSesion(){
        String usuario = _user.getText().toString();
        String pass = _password.getText().toString();

        Log.d("TAG3", "Llamando a iniciarSesion");

        auth.iniciarSesion(usuario, pass,
                ()->{
                    runOnUiThread(()->{
                        Log.d("TAG2", "Inicio de sesion exitoso");
                        startActivity(new Intent(this, DashboardActivity.class));
                        finish();
                    });
                },
                uid -> {
                    runOnUiThread(() -> mostrarDialogoMasterPass(uid));},
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
                        startActivity(new Intent(this, DashboardActivity.class));
                    });
                },
                mensaje -> {
                    Log.d("TAG2", mensaje);
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                });
    }

    private void registrarUsuario(){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }

}