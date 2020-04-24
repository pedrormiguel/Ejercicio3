package com.example.ejercicio2.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejercicio2.Estudiantes;
import com.example.ejercicio2.R;

import java.util.List;

public class AdapeterData extends RecyclerView.Adapter<AdapeterData.ViewHolderDatos> {

    List<Estudiantes> listDatos;

    public AdapeterData(List<Estudiantes> listDatos) {
        this.listDatos = listDatos; }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).
               inflate(R.layout.item_list,null,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapeterData.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public  class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView name;
        TextView id;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textviewRecyclerName);
            id = itemView.findViewById(R.id.textviewRecyclerId);
        }

        public void asignarDatos(Estudiantes estudiante) {
            name.setText(estudiante.name);
            id.setText(String.valueOf( estudiante.id));
        }
    }
}
