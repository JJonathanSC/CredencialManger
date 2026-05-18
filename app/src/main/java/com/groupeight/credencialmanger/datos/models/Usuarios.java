package com.groupeight.credencialmanger.datos.models;

public class Usuarios {
    private String uid;
    private String email;
    private String password;
    private String salt;

    public Usuarios(){

    }

    public Usuarios(String uid, String salt){
        this.uid = uid;
        //this.email = email;
        //this.password = password;
        this.salt = salt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
