package com.groupeight.credencialmanger.datos;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.groupeight.credencialmanger.datos.models.Credenciales;
import com.groupeight.credencialmanger.datos.models.Usuarios;

import java.util.Map;

public class FireBase {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    //Metodos de autenticación ----

    public FireBase(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public Task<AuthResult> registrarUsuario(String email, String password){
        return mAuth.createUserWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> iniciarSesion(String email, String password){
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    public FirebaseUser obtenerUsuarioActual(){
        return mAuth.getCurrentUser();
    }

    //Creacion y Obtencion de usuarios

    public Task<Void> guardarUsuario(Usuarios usuario){
        return db.collection("usuarios").document(usuario.getUid()).set(usuario);
    }

    public Task<DocumentSnapshot> obtenerUsuario(String uid){
        return db.collection("usuarios").document(uid).get();
    }

    // Credenciaeles

    public Task<Void> guardarCredencial(String uid, Credenciales credencial){
        return db.collection("usuarios")
                .document(uid)
                .collection("credenciales")
                .document()
                .set(credencial);
    }

    public Task<QuerySnapshot> obtenerCredenciales(String uid){
        return db.collection("usuarios")
                .document(uid)
                .collection("credenciales")
                .get();
    }

    public Task<QuerySnapshot> obtenerCredencialesByPackageName(String uid, String packageName){
        return db.collection("usuarios")
                .document(uid)
                .collection("credenciales")
                .whereEqualTo("packageName", packageName)
                .get();
    }

    public Task<QuerySnapshot> obtenerCredencialesByDominio(String uid, String dominio){
        return db.collection("usuarios")
                .document(uid)
                .collection("credenciales")
                .whereEqualTo("dominio", dominio)
                .get();
    }

    public Task<Void> eliminarCredencial(String uid, String credencialId){
        return db.collection("usuarios")
                .document(uid)
                .collection("credenciales")
                .document(credencialId)
                .delete();
    }

    public Task<Void> actualizarCredencial(String uid, String docId, Map<String, Object> campos){
        return db.collection("usuarios")
                .document(uid)
                .collection("credenciales")
                .document(docId)
                .update(campos);
    }

}
