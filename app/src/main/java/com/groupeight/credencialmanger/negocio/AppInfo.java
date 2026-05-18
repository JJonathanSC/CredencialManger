package com.groupeight.credencialmanger.negocio;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private String nombre;
    private String packageName;
    private Drawable icono;

    public AppInfo(String nombre, String packageName, Drawable icono){
        this.nombre = nombre;
        this.packageName = packageName;
        this.icono = icono;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcono() {
        return icono;
    }

    public void setIcono(Drawable icono) {
        this.icono = icono;
    }
}
