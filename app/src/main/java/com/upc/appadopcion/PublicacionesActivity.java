package com.upc.appadopcion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.appadopcion.R;
import com.upc.appadopcion.model.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class PublicacionesActivity extends AppCompatActivity {

    RecyclerView rvPublicacion;
    FloatingActionButton btnNuevaPublicacion;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<Publicacion> listaPublicaciones = new ArrayList<>();
    AdaptadorPublicacion adaptadorPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        inicializarFirebase();
        asignarReferencias();
        listarPublicaciones();
    }

    private void listarPublicaciones() {

        reference.child("Publicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPublicaciones.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    Publicacion p = item.getValue(Publicacion.class);
                    listaPublicaciones.add(p);
                }
                adaptadorPublicacion = new AdaptadorPublicacion(PublicacionesActivity.this, listaPublicaciones);
                rvPublicacion.setAdapter(adaptadorPublicacion);
                rvPublicacion.setLayoutManager(new LinearLayoutManager(PublicacionesActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void asignarReferencias() {
        rvPublicacion = findViewById(R.id.rvPublicacion);
        btnNuevaPublicacion = findViewById(R.id.btnNuevaPublicacion);
        btnNuevaPublicacion.setOnClickListener(view -> {
            Intent intent = new Intent(this, PublicacionActivity.class);
            startActivity(intent);
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

    }
}