package com.groupeight.credencialmanger.presentacion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.negocio.AppInfo;

import java.util.List;

public class AppAdapter extends ArrayAdapter<AppInfo> {
    private Context context;
    private List<AppInfo> apps;

    public AppAdapter(Context context, List<AppInfo> apps){
        super(context, 0, apps);
        this.context = context;
        this.apps = apps;
    }

    public View getView(int posicion, View view, ViewGroup parent){
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listaapps, parent, false);
        }

        AppInfo app = apps.get(posicion);

        ImageView ivIcono = view.findViewById(R.id.ivAppLista);
        TextView tvNombre = view.findViewById(R.id.tvAppLista);
        TextView tvPackageName = view.findViewById(R.id.tvPackageNameLista);

        tvNombre.setText(app.getNombre());
        tvPackageName.setText(app.getPackageName());
        ivIcono.setImageDrawable(app.getIcono());

        return view;
    }

}

