package com.groupeight.credencialmanger.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.AppInfo;
import com.groupeight.credencialmanger.negocio.AppManager;
import com.groupeight.credencialmanger.presentacion.Adapter.AppAdapter;

import java.util.ArrayList;
import java.util.List;

public class AgregarCredencialActivity extends AppCompatActivity {
    EditText edtNombreCuenta;
    ListView lvApps;
    AppManager appManager;
    AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_credencial);

        appManager = new AppManager();
        lvApps = findViewById(R.id.lvApps);

        cargarApps();
    }

    private void cargarApps(){
        List<AppInfo> apps = new ArrayList<>();

        //Primera opción del ListView permite crear una cuenta de forma manual
        apps.add(new AppInfo("Crear manual", null, null));

        //Cargamos las apps instaladas
        apps.addAll(appManager.obtenerAppsInstaladas(this));

        adapter = new AppAdapter(this, apps);
        lvApps.setAdapter(adapter);

        lvApps.setOnItemClickListener((parent, view, position, id)->{
            AppInfo appSeleccionada = adapter.getItem(position);

            Intent intent = new Intent(this, FormularioCredencial.class);

            if (appSeleccionada.getPackageName() != null){
                intent.putExtra("packageName", appSeleccionada.getPackageName());
                intent.putExtra("nombreApp", appSeleccionada.getNombre());
            }

            startActivity(intent);
        });

    }

}