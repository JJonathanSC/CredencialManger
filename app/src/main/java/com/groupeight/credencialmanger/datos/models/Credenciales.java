package com.groupeight.credencialmanger.datos.models;

public class Credenciales {
    private String cuenta;
    private String usuario;
    private String password;
    private String packageName;
    private String dominio;
    private String docId;

    public Credenciales(){}

    public Credenciales(String cuenta, String usuario, String password, String packageName, String dominio){
        this.cuenta = cuenta;
        this.usuario = usuario;
        this.password = password;
        this.packageName = packageName;
        this.dominio = dominio;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String domain) {
        this.dominio = domain;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
