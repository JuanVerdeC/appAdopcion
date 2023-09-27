package com.upc.appadopcion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.appadopcion.model.Adopcion;

import java.util.ArrayList;
import java.util.List;

public class ActivityListar extends AppCompatActivity {

    FloatingActionButton btnNuevoA;
    ListView lstAdopcion;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    List<Adopcion> listaAdopcion=new ArrayList<>();
    ArrayAdapter<Adopcion> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        inicializarFirebase();
        listarDatos();
    }

    private void asignarReferencias(){
        btnNuevoA=findViewById(R.id.btnNuevoAdopcion);
        btnNuevoA.setOnClickListener(view -> {
            Intent intent= new Intent(this, Registrar_adopcionActivity.class);
            startActivity(intent);
        });
        lstAdopcion=findViewById(R.id.lstAdopcion);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void listarDatos(){
        reference.child("Adopcion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaAdopcion.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    Adopcion a=item.getValue(Adopcion.class);
                    listaAdopcion.add(a);
                }
                adaptador=new ArrayAdapter<Adopcion>(ActivityListar.this, android.R.layout.simple_list_item_1,listaAdopcion);
                lstAdopcion.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}