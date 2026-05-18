package com.groupeight.credencialmanger.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.datos.models.Credenciales;
import com.groupeight.credencialmanger.negocio.CredencialManager;
import com.groupeight.credencialmanger.presentacion.Adapter.CredencialAdapter;

import java.util.List;

public class MostrarCredenciales extends AppCompatActivity {

    private RecyclerView rvCredenciales;
    private CredencialManager credencialManager;
    private CredencialAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_credenciales);

        rvCredenciales = findViewById(R.id.rvCredenciales);
        rvCredenciales.setLayoutManager(new GridLayoutManager(this,1));

        credencialManager = new CredencialManager();

        Button btnAgregar = findViewById(R.id.btnAgregarCredencialMostrar);
        btnAgregar.setOnClickListener(v ->
                startActivity(new Intent(this, AgregarCredencialActivity.class)));

        cargarCredenciales();
    }


    private void cargarCredenciales(){
        credencialManager.obtenerCredenciales(
                credenciales-> runOnUiThread(() ->mostrarCredenciales(credenciales)),
                mensaje -> runOnUiThread(()->Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show()));
    }

    private void mostrarCredenciales(List<Credenciales> credenciales){
        adapter = new CredencialAdapter(this, credenciales, new CredencialAdapter.OnCredencialListener() {
            @Override
            public void onEditar(Credenciales credenciales, String docId) {

            }

            @Override
            public void onEliminar(String docId) {
                credencialManager.eliminarCredencial(docId,
                        ()-> runOnUiThread(()->{
                            Toast.makeText(MostrarCredenciales.this, "Credencial eliminada", Toast.LENGTH_SHORT).show();
                            cargarCredenciales();
                        }),
                        mensaje -> runOnUiThread(()-> Toast.makeText(MostrarCredenciales.this, mensaje, Toast.LENGTH_SHORT).show())
                );
            }
        });

        rvCredenciales.setAdapter(adapter);
    }
}