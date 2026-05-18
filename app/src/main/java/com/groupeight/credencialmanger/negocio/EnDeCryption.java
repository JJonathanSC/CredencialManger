package com.groupeight.credencialmanger.negocio;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class EnDeCryption {
    private String algoritmo = "AES/GCM/NoPadding";
    private int ivLength = 12;

    public String Cifrar(String password, SecretKey claveSecreta) throws Exception{


        //inicializamos la clase Cipher en modo cifrado
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.ENCRYPT_MODE, claveSecreta);

        //Recuperamos el IV generado por el KeyStore
        byte[] iv = cipher.getIV();
        byte[] textoCifrado = cipher.doFinal(password.getBytes());

        //Concatenamos el iv y el texto cifrado
        byte[] resultado= new byte[iv.length + textoCifrado.length];
        System.arraycopy(iv, 0, resultado, 0, iv.length);
        System.arraycopy(textoCifrado, 0, resultado, iv.length, textoCifrado.length);

        return Base64.encodeToString(resultado, Base64.NO_WRAP);
    }

    public String descifrar(String textoCifradoBase64, SecretKey claveSecreta) throws Exception{
        byte[] datos = Base64.decode(textoCifradoBase64, Base64.NO_WRAP);

        //separamos el texto cifrado y el iv
        byte[] iv = new byte[ivLength];
        byte[] textoCifrado = new byte[datos.length - ivLength];
        System.arraycopy(datos, 0, iv, 0, ivLength);
        System.arraycopy(datos, ivLength, textoCifrado, 0, textoCifrado.length);

        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.DECRYPT_MODE, claveSecreta, new GCMParameterSpec(128, iv));

        return new String(cipher.doFinal(textoCifrado));
    }
}
