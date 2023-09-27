package com.upc.appadopcion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.appadopcion.model.Adopcion;

import java.util.UUID;

public class Registrar_adopcionActivity extends AppCompatActivity {

    EditText txtNombreA, txtDNI, txtNacimiento,txtCorreo, txtCelular, txtDireccion, txtDistrito;

    Button btnCancelarA, btnRegistrarA;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_adopcion);
        inicializarFirebase();
        asignarReferencias();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void asignarReferencias() {
        txtNombreA = findViewById(R.id.txtNombreA);
        txtDNI = findViewById(R.id.txtDNI);
        txtNacimiento = findViewById(R.id.txtNacimiento);
        txtCelular = findViewById(R.id.txtCelular);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtDistrito = findViewById(R.id.txtDistrito);

        btnCancelarA.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityListar.class);
            startActivity(intent);
        });

        btnRegistrarA.setOnClickListener(view -> {
            if (validarDatos()) {
                String mensaje = "";
                mostrarMensaje(mensaje);
            }
        });
    }
        private void registrar () {
            String nombres=txtNombreA.getText().toString();
            String dni=txtDNI.getText().toString();
            String nacimiento=txtNacimiento.getText().toString();
            String celular=txtCelular.getText().toString();
            String correo=txtCorreo.getText().toString();
            String direccion=txtDireccion.getText().toString();
            String distrito=txtDistrito.getText().toString();

            Adopcion a= new Adopcion();
            a.setUid(UUID.randomUUID().toString());
            a.setNombre(nombres);
            a.setDni(Integer.parseInt(dni));
            a.setFechanacimiento(nacimiento);
            a.setCelular(Integer.parseInt(celular));
            a.setCorreo(correo);
            a.setDireccion(direccion);
            a.setDistrito(distrito);

            reference.child("Adopcion").child(a.getUid()).setValue(a);
            mostrarMensaje("Adopcion registrada");


        }

    private void mostrarMensaje(String mensaje){
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana.setTitle("Mensaje Informativo");
        ventana.setMessage(mensaje);
        ventana.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            Intent intent = new Intent(this, ActivityListar.class);
            startActivity(intent);
        });
        ventana.create().show();
    }
        private Boolean validarDatos () {

            boolean valida = true;

            String nombres = txtNombreA.getText().toString();
            String dni = txtDNI.getText().toString();
            String nacimiento = txtNacimiento.getText().toString();
            String celular = txtCelular.getText().toString();
            String correo = txtCorreo.getText().toString();
            String direccion = txtDireccion.getText().toString();
            String distrito = txtDistrito.getText().toString();

            if (nombres.equals("")) {
                txtNombreA.setError("Los nombres son obligatorios");
                valida = false;
            }
            if (dni.equals("")) {
                txtDNI.setError("El DNI es obligatorio");
                valida = false;
            }
            if (nacimiento.equals("")) {
                txtNacimiento.setError("La fecha de nacimiento es obligatoria");
                valida = false;
            }
            if (celular.equals("")) {
                txtCelular.setError("El celular es obligatorio");
                valida = false;
            }
            if (correo.equals("")) {
                txtCorreo.setError("El correo es obligatorio");
                valida = false;
            }
            if (direccion.equals("")) {
                txtDireccion.setError("La direccion es obligatoria");
                valida = false;
            }
            if (distrito.equals("")) {
                txtDistrito.setError("El distrito es obligatorio");
                valida = false;
            }
            return valida;
    }
}