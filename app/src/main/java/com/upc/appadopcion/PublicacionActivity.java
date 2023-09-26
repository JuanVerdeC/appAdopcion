package com.upc.appadopcion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.appadopcion.model.Publicacion;
import com.upc.appadopcion.model.Usuario;

import java.util.UUID;

public class PublicacionActivity extends AppCompatActivity {

    EditText txtNombreMascota, txtEdadMascota, txtDescripcionMascota;
    Button btnPublicaMascota;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);
        inicializarFirebase();
        asignarReferencias();
    }

    private void asignarReferencias() {
        txtNombreMascota = findViewById(R.id.txtNombreMascota);
        txtEdadMascota = findViewById(R.id.txtEdadMascota);
        txtDescripcionMascota = findViewById(R.id.txtDescripcionMascota);
        btnPublicaMascota = findViewById(R.id.btnPublicaMascota);
        btnPublicaMascota.setOnClickListener(view -> {
            registrar();
        });

    }

    private Boolean validarDatos() {

        boolean valida = true;

        String nombre = txtNombreMascota.getText().toString();
        String edad = txtEdadMascota.getText().toString();
        String descripcion = txtDescripcionMascota.getText().toString();


        if(nombre.equals("")){
            txtNombreMascota.setError("Obligatorio");
            valida =false;
        }
        if (edad.equals("")){
            txtEdadMascota.setError("Obligatorio");
            valida =false;
        }
        if (descripcion.equals("")){
            txtDescripcionMascota.setError("Obligatorio");
            valida =false;
        }
        return valida;
    }
    private void registrar() {
        if (validarDatos()){
            String nombre = txtNombreMascota.getText().toString();
            String edad = txtEdadMascota.getText().toString();
            String descripcion = txtDescripcionMascota.getText().toString();

            Publicacion p = new Publicacion();
            p.setUid(UUID.randomUUID().toString());
            p.setNombreMascota(nombre);
            p.setEdad(Integer.parseInt(edad));
            p.setDescripcion(descripcion);

            reference.child("Publicacion").child(p.getUid()).setValue(p);
            mostrarMensaje("Publicación realizada");
        }


    }
    private void mostrarMensaje(String mensaje){
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana.setTitle("Mensaje");
        ventana.setMessage(mensaje);
        ventana.setPositiveButton("Aceptar", (dialogInterface, which) -> {
            Intent intent = new Intent(this, PublicacionesActivity.class);
            startActivity(intent);
        } );
        ventana.create().show();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }
}