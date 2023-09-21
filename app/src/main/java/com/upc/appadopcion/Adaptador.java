package com.upc.appadopcion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.appadopcion.modelo.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MiViewHolder> {

    private Context context;
    private List<Publicacion> listaPublicaciones = new ArrayList<>();

    public Adaptador(Context context, List<Publicacion> listaPublicaciones){
        this.context = context;
        this.listaPublicaciones = listaPublicaciones;
    }
    @NonNull
    @Override
    public Adaptador.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.fila,parent, false   );
        return new MiViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.MiViewHolder holder, int position) {
        holder.filaNombre.setText(listaPublicaciones.get(position).getNombre()+"");
    }

    @Override
    public int getItemCount() {
        return listaPublicaciones.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        TextView filaNombre;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            filaNombre = itemView.findViewById(R.id.filaNombre);
        }
    }
}
