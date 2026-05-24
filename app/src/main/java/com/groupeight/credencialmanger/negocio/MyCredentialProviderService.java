package com.groupeight.credencialmanger.negocio;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.CancellationSignal;
import android.os.OutcomeReceiver;

import androidx.annotation.NonNull;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.CreateCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.NoCredentialException;
import androidx.credentials.provider.BeginCreateCredentialRequest;
import androidx.credentials.provider.BeginCreateCredentialResponse;
import androidx.credentials.provider.BeginGetCredentialOption;
import androidx.credentials.provider.BeginGetCredentialRequest;
import androidx.credentials.provider.BeginGetCredentialResponse;
import androidx.credentials.provider.BeginGetPasswordOption;
import androidx.credentials.provider.CredentialEntry;
import androidx.credentials.provider.CredentialProviderService;
import androidx.credentials.provider.PasswordCredentialEntry;
import androidx.credentials.provider.ProviderClearCredentialStateRequest;

import com.groupeight.credencialmanger.datos.models.Credenciales;
import com.groupeight.credencialmanger.presentacion.CredentialEntrega;

import java.util.ArrayList;
import java.util.List;

public class MyCredentialProviderService extends CredentialProviderService {

    private CredencialManager credencialManager;


    @Override
    public void onClearCredentialStateRequest(@NonNull ProviderClearCredentialStateRequest providerClearCredentialStateRequest, @NonNull CancellationSignal cancellationSignal, @NonNull OutcomeReceiver<Void, ClearCredentialException> outcomeReceiver) {

    }

    @Override
    public void onBeginGetCredentialRequest(@NonNull BeginGetCredentialRequest beginGetCredentialRequest, @NonNull CancellationSignal cancellationSignal, @NonNull OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException> outcomeReceiver) {
        //Obtenemos el nombre del paquete de la appCliente

        String packageName = beginGetCredentialRequest.getCallingAppInfo().getPackageName();
        List<CredentialEntry> entries = new ArrayList<>();

        for (BeginGetCredentialOption option : beginGetCredentialRequest.getBeginGetCredentialOptions()){
            if (option instanceof BeginGetPasswordOption){
                credencialManager.obtenerCredencialByPackage(packageName,
                        credenciales -> {
                        for (Credenciales credencial : credenciales ){
                            Intent intent = new Intent(this, CredentialEntrega.class);
                            intent.putExtra("usuario", credencial.getUsuario());
                            intent.putExtra("password", credencial.getPassword());

                            PendingIntent pendingIntent = PendingIntent.getActivity(
                                    this,
                                    0,
                                    intent,
                                    PendingIntent.FLAG_MUTABLE
                            );

                            PasswordCredentialEntry entry = new PasswordCredentialEntry.Builder(
                                    this,
                                    credencial.getCuenta(),
                                    pendingIntent,
                                    (BeginGetPasswordOption) option
                            ).build();

                            entries.add(entry);
                        }

                        BeginGetCredentialResponse response = new BeginGetCredentialResponse(
                                entries,
                                new ArrayList<>(),
                                new ArrayList<>(),
                                null
                        );
                        outcomeReceiver.onResult(response);
                },
                        mensaje -> {
                            outcomeReceiver.onError(new NoCredentialException(mensaje));
                        }
                );
            }
        }
    }

    @Override
    public void onBeginCreateCredentialRequest(@NonNull BeginCreateCredentialRequest beginCreateCredentialRequest, @NonNull CancellationSignal cancellationSignal, @NonNull OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException> outcomeReceiver) {

    }
}
