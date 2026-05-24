package com.groupeight.credencialmanger.presentacion;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.PasswordCredential;
import androidx.credentials.provider.PendingIntentHandler;

import com.groupeight.credencialmanger.R;

public class CredentialEntrega extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = getIntent().getStringExtra("usuario");
        String password = getIntent().getStringExtra("password");
        Log.d("usuario", username);

        // Empaquetar la credencial y devolvérsela a Android
        PasswordCredential credential = new PasswordCredential(username, password);
        GetCredentialResponse response = new GetCredentialResponse(credential);
        PendingIntentHandler.setGetCredentialResponse(getIntent(), response);

        setResult(RESULT_OK, getIntent());
        finish();
    }
}