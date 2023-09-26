package com.upc.appadopcion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.upc.appadopcion.model.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPublicacion extends RecyclerView.Adapter<AdaptadorPublicacion.MiViewHolder> {
    private Context context;
    private List<Publicacion> listaPublicaciones = new ArrayList<>();

    public AdaptadorPublicacion(Context context, List<Publicacion> listaPublicaciones){
        this.context = context;
        this.listaPublicaciones= listaPublicaciones;
    }

    @NonNull
    @Override
    public AdaptadorPublicacion.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.filapublicacion,parent, false);
        return new MiViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPublicacion.MiViewHolder holder, int position) {
        holder.filaNombrePublicacion.setText(listaPublicaciones.get(position).getNombreMascota()+"");
        holder.filaEdadPublicacion.setText(listaPublicaciones.get(position).getEdad()+"");
        holder.filaDescripcionPublicacion.setText(listaPublicaciones.get(position).getDescripcion()+"");
        holder.filaBtnDetalles.setOnClickListener(view -> {
            Intent intent = new Intent(context, PublicacionDetalladaActivity.class);
            intent.putExtra("var_nombre",listaPublicaciones.get(position).getNombreMascota());
            intent.putExtra("var_edad",listaPublicaciones.get(position).getEdad());
            intent.putExtra("var_descripcion",listaPublicaciones.get(position).getDescripcion());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaPublicaciones.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        TextView filaNombrePublicacion, filaEdadPublicacion, filaDescripcionPublicacion;
        ImageButton filaBtnDetalles;



        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            filaNombrePublicacion = itemView.findViewById(R.id.filaPublicacionNombre);
            filaEdadPublicacion = itemView.findViewById(R.id.filaPublicacionEdad);
            filaDescripcionPublicacion = itemView.findViewById(R.id.filaPublicacionDescripcion);
            filaBtnDetalles = itemView.findViewById(R.id.filaPublicacionDetalles);


        }
    }
}
