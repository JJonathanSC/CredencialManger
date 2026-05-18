package com.groupeight.credencialmanger.negocio;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.groupeight.credencialmanger.datos.FireBase;
import com.groupeight.credencialmanger.datos.models.Credenciales;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

public class CredencialManager {
    private FireBase fireBase;
    private KeyStoreManager keyStoreManager;
    private EnDeCryption enDeCryption;

    public CredencialManager(){
        fireBase = new FireBase();
        keyStoreManager = new KeyStoreManager();
        enDeCryption = new EnDeCryption();
    }

    public void guardarCredencial(String nombreCuenta, String usuario, String password,
                                  String packageName, String dominio,
                                  Autenticacion.OnSuccesListener onSucces,
                                  Autenticacion.OnErrorListener onError){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        try {
            SecretKey clave = (SecretKey) keyStoreManager.getClaveSecreta();

            //Ciframos la contraseña
            String passCifrada = enDeCryption.Cifrar(password, clave);

            Credenciales credenciales = new Credenciales(
                   nombreCuenta,
                   usuario,
                   passCifrada,
                   packageName,
                   dominio
            );

            fireBase.guardarCredencial(uid, credenciales)
                    .addOnSuccessListener(unused -> onSucces.onSuccess())
                    .addOnFailureListener(e -> onError.onError("Error al guardar credencial: " + e.getMessage()));

        }catch (Exception e){
            Log.d("TAG3", e.getMessage());
            onError.onError("Error" + e.getMessage());
        }
    }

    public void obtenerCredenciales(OnCredencialListener onCredencial, Autenticacion.OnErrorListener onError){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fireBase.obtenerCredenciales(uid)
                .addOnSuccessListener(querySnapshots ->{
                    try{
                        SecretKey clave = (SecretKey) keyStoreManager.getClaveSecreta();
                        List<Credenciales> credenciales = new ArrayList<>();

                        for (DocumentSnapshot doc : querySnapshots.getDocuments()){
                            Credenciales credencial = doc.toObject(Credenciales.class);
                            credencial.setDocId(doc.getId());
                            credencial.setPassword(enDeCryption.descifrar(credencial.getPassword(), clave));
                            credenciales.add(credencial);
                        }

                        onCredencial.onCredencial(credenciales);
                    }catch (Exception e){
                        onError.onError("Error al descifrar los datos");
                    }

                }).addOnFailureListener(e -> onError.onError("Error al obtener credenciales: " + e.getMessage()));
    }

    public void eliminarCredencial(String credencialId, Autenticacion.OnSuccesListener onSuccess, Autenticacion.OnErrorListener onError){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fireBase.eliminarCredencial(uid, credencialId)
                .addOnSuccessListener(unused -> onSuccess.onSuccess())
                .addOnFailureListener(e -> onError.onError("Error al eliminar la credencial"));
    }


    public interface OnCredencialListener{
        void onCredencial(List<Credenciales> credenciales);
    }
}
