package com.groupeight.credencialmanger.presentacion;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.groupeight.credencialmanger.R;

import java.security.SecureRandom;

public class GenerarPasswordActivity extends AppCompatActivity {

    EditText edtLongitud;
    TextView tvGeneratedPassword;
    MaterialButton btnGenerar, btnCopiar;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generar_password);

        edtLongitud = findViewById(R.id.edtLongitudGenerarPass);
        tvGeneratedPassword = findViewById(R.id.tvGeneratedPassword);
        btnGenerar = findViewById(R.id.btnGenerarPass);
        btnCopiar = findViewById(R.id.btnCopiarGenerarPass);

        btnGenerar.setOnClickListener(v -> generarPassword());
        btnCopiar.setOnClickListener(v -> copiar());
    }

    private void generarPassword(){
        String longitud = edtLongitud.getText().toString().trim();

        if (longitud.isEmpty()){
            Toast.makeText(this, "Ingresa la longitud de la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        int length = Integer.parseInt(longitud);

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        SecureRandom random = new SecureRandom();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <= length; i++){
            int index = random.nextInt(caracteres.length());
            stringBuilder.append(caracteres.charAt(index));
        }

        password = stringBuilder.toString();
        tvGeneratedPassword.setText(password);
    }

    private void copiar(){
        if (password.isEmpty()){
            Toast.makeText(this, "Genere una contraseña primero",Toast.LENGTH_SHORT).show();
            return;
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText("password", password);
        clipboard.setPrimaryClip(clipData);

        Toast.makeText(this, "Contraseña copiada", Toast.LENGTH_SHORT).show();
    }

}