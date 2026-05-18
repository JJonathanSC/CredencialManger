package com.groupeight.credencialmanger.negocio;

import android.security.keystore.KeyProperties;
import android.security.keystore.KeyProtection;
import android.util.Base64;
import android.util.Log;

import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyStoreManager {
    private String keyStoreProvider = "AndroidKeyStore";
    private String alias = "miClaveSecreta";

    public String generarSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return Base64.encodeToString(salt,Base64.NO_WRAP);
    }

    public void derivarYGuardarClave(String masterPassword, String saltBase64) throws Exception
    {
        // Derivar la contraseña maestra con PBKDF2
        byte[] salt = Base64.decode(saltBase64, Base64.NO_WRAP);

        PBEKeySpec spec = new PBEKeySpec(
                masterPassword.toCharArray(),
                salt,
                200000,
                256
        );

        SecretKeyFactory scf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] claveBytes = scf.generateSecret(spec).getEncoded();
        spec.clearPassword();

        SecretKey clave = new SecretKeySpec(claveBytes,"AES");

        //Guardar la clave derivada en el KeyStore

        KeyStore ks = KeyStore.getInstance(keyStoreProvider);
        ks.load(null);

        KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(clave);

        KeyProtection protection = new KeyProtection.Builder(
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build();

        ks.setEntry(alias, entry, protection);
    }

    //Obtenemos la clave secreta
    public SecretKey getClaveSecreta() throws Exception{
        KeyStore kStore = KeyStore.getInstance(keyStoreProvider);
        kStore.load(null);
        return (SecretKey) kStore.getKey(alias,null);

    }

    //Validamos si ya existe una clave secreta
    public boolean existeClave() throws Exception{
        KeyStore kStore = KeyStore.getInstance(keyStoreProvider);
        kStore.load(null);

        return kStore.containsAlias(alias);
    }

    public void eliminarClave() throws Exception{
        KeyStore ks = KeyStore.getInstance(keyStoreProvider);
        ks.load(null);
        ks.deleteEntry(alias);
    }
}
