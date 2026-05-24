package com.groupeight.credencialmanger.presentacion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groupeight.credencialmanger.R;
import com.groupeight.credencialmanger.datos.models.Credenciales;
import com.groupeight.credencialmanger.presentacion.FormularioCredencial;

import java.util.List;

public class CredencialAdapter extends RecyclerView.Adapter<CredencialAdapter.CredencialViewHolder> {

    private Context context;
    private List<Credenciales> credenciales;
    private OnCredencialListener listener;

    public interface OnCredencialListener{
        void onEditar(Credenciales credenciales, String docId);
        void onEliminar(String docId);
    }

    public CredencialAdapter(Context context, List<Credenciales> credenciales, OnCredencialListener listener){
        this.context = context;
        this.credenciales = credenciales;
        this.listener = listener;
    }

    public CredencialViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
       View view = LayoutInflater.from(context).inflate(R.layout.item_credencial, parent, false);
       return new CredencialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CredencialViewHolder holder, int position) {
        Credenciales credencial = credenciales.get(position);

        holder.tvCuenta.setText(credencial.getCuenta());
        holder.tvUsario.setText(credencial.getUsuario());
        holder.tvPassword.setText(credencial.getPassword());

        holder.btnEliminar.setOnClickListener(v ->
                listener.onEliminar(credencial.getDocId()));

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormularioCredencial.class);
            intent.putExtra("modo", "editar");
            intent.putExtra("docId", credencial.getDocId());
            intent.putExtra("cuenta", credencial.getCuenta());
            intent.putExtra("usuario", credencial.getUsuario());
            intent.putExtra("password", credencial.getPassword());
            intent.putExtra("dominio",credencial.getDominio());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return credenciales.size();
    }

    public static class CredencialViewHolder extends RecyclerView.ViewHolder{
        TextView tvCuenta, tvUsario, tvPassword;
        ImageButton btnEditar, btnEliminar;

        public CredencialViewHolder(View itemView){
            super(itemView);

            tvCuenta = itemView.findViewById(R.id.tvCuentaCard);
            tvUsario = itemView.findViewById(R.id.tvUsuarioCard);
            tvPassword = itemView.findViewById(R.id.tvPassCard);
            btnEditar = itemView.findViewById(R.id.btnEditarCard);
            btnEliminar = itemView.findViewById(R.id.btnEliminarCard);

        }

    }

}
