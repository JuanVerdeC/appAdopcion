package com.upc.appadopcion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.appadopcion.modelo.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class ListarPublicacionActivity extends AppCompatActivity {

    List<Publicacion> listaPublicaciones = new ArrayList<>();
    Adaptador adaptador;

    RecyclerView rvPublicaciones;
    FloatingActionButton btnNuevo;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_publicacion);
        asignarReferencias();
        mostrarPublicaciones();
        inicializarFirebase();
        
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    private void asignarReferencias() {
        rvPublicaciones = findViewById(R.id.rvPublicaciones);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void mostrarPublicaciones() {

        adaptador = new Adaptador(this, listaPublicaciones);
        rvPublicaciones.setAdapter(adaptador);
        rvPublicaciones.setLayoutManager(new LinearLayoutManager(this));
    }
    private void listarDatos(){
        reference.child("Publicacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}