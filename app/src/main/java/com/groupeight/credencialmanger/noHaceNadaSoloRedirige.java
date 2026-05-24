package com.groupeight.credencialmanger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.GetPasswordOption;
import androidx.credentials.PasswordCredential;
import androidx.credentials.exceptions.GetCredentialException;

import com.groupeight.credencialmanger.presentacion.AgregarCredencialActivity;
import com.groupeight.credencialmanger.presentacion.MostrarCredenciales;

import java.util.concurrent.Executors;

public class noHaceNadaSoloRedirige extends AppCompatActivity {

    private CredentialManager credentialManager;

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

        credentialManager = CredentialManager.create(this);

        Button btnAgregar = findViewById(R.id.btnAgregarFrmTemp);
        Button btnMostrar = findViewById(R.id.btnMostrarFrmTemp);

        btnMostrar.setOnClickListener(v1 ->
                startActivity(new Intent(this, MostrarCredenciales.class)));

        btnAgregar.setOnClickListener(v1 ->
                startActivity(new Intent(this, AgregarCredencialActivity.class)));
    }

    public void solicitarCredenciales(View view) {
        GetPasswordOption getPasswordOption = new GetPasswordOption();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(getPasswordOption)
                .build();

        credentialManager.getCredentialAsync(
                this,
                request,
                null,
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {

                    @Override
                    public void onResult(GetCredentialResponse result) {
                        Credential credential = result.getCredential();

                        if (credential instanceof PasswordCredential) {
                            PasswordCredential passwordCredential = (PasswordCredential) credential;
                            String username = passwordCredential.getId();
                            String password = passwordCredential.getPassword();

                            runOnUiThread(() -> {

                                Log.d("entraaa","hola :)");

                                /*
                                EditText user = findViewById(R.id.editTextText);
                                EditText pass = findViewById(R.id.editTextTextPassword);

                                user.setText(username);
                                pass.setText(password);*/

                                Log.d("CredentialManager5", "Usuario: " + username);
                                Log.d("CredentialManager5", "Password: " + password);
                            });
                        }
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        runOnUiThread(() ->
                                Log.e("CredentialManagerError", "Error: " + e.getMessage())
                        );
                    }
                }
        );
    }
}