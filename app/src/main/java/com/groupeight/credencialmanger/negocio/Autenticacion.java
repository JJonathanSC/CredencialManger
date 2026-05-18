package com.groupeight.credencialmanger.negocio;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.groupeight.credencialmanger.datos.FireBase;
import com.groupeight.credencialmanger.datos.models.Credenciales;
import com.groupeight.credencialmanger.datos.models.Usuarios;

import javax.crypto.SecretKey;

public class Autenticacion {
    private FireBase fireBase;
    private KeyStoreManager keyStoreManager;

    public Autenticacion (){
        fireBase = new FireBase();
        keyStoreManager = new KeyStoreManager();
    }

    public void registrarUsuario(String email, String password, String masterPassword,
                                 OnSuccesListener onSuccess, OnErrorListener onError)
    {
        fireBase.registrarUsuario(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();

                    //Generamos la salt
                    String salt = keyStoreManager.generarSalt();

                    //Derivamos la contraseña maestra y la almacenamos en el keyStore}
                    try{
                        keyStoreManager.derivarYGuardarClave(masterPassword, salt);
                    }catch (Exception e){
                        onError.onError("Error al guardar la clave:" + e.getMessage());
                        return;
                    }

                    //Guardamos el usuario en la nube

                    Usuarios usuario = new Usuarios(uid, salt);
                    fireBase.guardarUsuario(usuario)
                            .addOnSuccessListener(unused -> onSuccess.onSuccess())
                            .addOnFailureListener(e -> onError.onError("Error al guardar el usuario" + e.getMessage()));
                })
                .addOnFailureListener(e -> onError.onError("Error al resgistrar: " + e.getMessage()));
    }

    public void iniciarSesion(String email, String password,
                              OnSuccesListener onSuccess,
                              OnNecesitaMasterPassword onNecesitaMasterPassword,
                              OnErrorListener onError){

        Log.d("TAG3", "Iniciando sesión con: " + email);  // agregar aquí

        fireBase.iniciarSesion(email, password)
                .addOnSuccessListener(authResult -> {
                    try {
                        //Validamos que la clave secreta exista
                        if (keyStoreManager.existeClave()){
                            //Se inicia sesión desde el mismo dispositivo
                            onSuccess.onSuccess();
                        }
                        else {
                            //inicia sesion desde un nuevo dispositivo
                            String uid = authResult.getUser().getUid();
                            onNecesitaMasterPassword.onNecesita(uid);
                        }
                    }catch (Exception e){
                        onError.onError("Error al verificar la clave: " + e.getMessage());
                    }
                }).addOnFailureListener(e -> {
                    onError.onError("Error al iniciar sesion" + e.getMessage());
                });
    }

    public void restaurarClaveSecreta(String uid, String masterPassword,
                                      OnSuccesListener onSuccess, OnErrorListener onError) {

        fireBase.obtenerUsuario(uid)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()){
                        String salt = documentSnapshot.getString("salt");
                        try{
                            keyStoreManager.derivarYGuardarClave(masterPassword, salt);
                            verificarClave(uid, onSuccess, onError);
                        }catch (Exception e){
                            onError.onError("Error al restaurar clave" + e.getMessage());
                        }
                    }
                    else {
                        onError.onError("usuario no encontrado");
                    }
                }).addOnFailureListener(e -> onError.onError("Error al restaurar la clave" + e.getMessage()));
    }

    private void verificarClave(String uid, OnSuccesListener onSuccess, OnErrorListener onError){
        fireBase.obtenerCredenciales(uid)
                .addOnSuccessListener(querySnapshot ->{
                    if (querySnapshot.isEmpty()){
                        //No hay credenciales registradas
                        onSuccess.onSuccess();
                        return;
                    }

                    try{
                        SecretKey clave = (SecretKey) keyStoreManager.getClaveSecreta();
                        DocumentSnapshot doc = querySnapshot.getDocuments().get(0);
                        Credenciales credenciales = doc.toObject(Credenciales.class);

                        new EnDeCryption().descifrar(credenciales.getPassword(), clave);

                        onSuccess.onSuccess();
                    }catch (Exception e){
                        try{
                            keyStoreManager.eliminarClave();
                        }catch (Exception e2){}

                        onError.onError("Contraseña maestra incorrecta");

                    }
                });
    }

    // Interfaces para callbacks
    public interface OnSuccesListener{
        void onSuccess();
    }

    public interface  OnErrorListener{
        void onError(String mensaje);
    }

    public interface OnNecesitaMasterPassword{
        void onNecesita(String uid);
    }
}
