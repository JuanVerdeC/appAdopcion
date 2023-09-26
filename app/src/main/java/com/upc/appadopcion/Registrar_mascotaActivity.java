package com.upc.appadopcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registrar_mascotaActivity extends AppCompatActivity {

    EditText txtNombreMascota, txtRazaMascota, txtEdadMascota, txtPeso, txtEnfermedades, txtHistoria;

    Button btnCancelarM, btnRegistrarM;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }
}