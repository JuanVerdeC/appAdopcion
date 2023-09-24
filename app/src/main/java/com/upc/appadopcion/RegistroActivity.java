package com.upc.appadopcion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.upc.appadopcion.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegistroActivity extends AppCompatActivity {

    Spinner spinner;
    Button btnCancelar, btnRegistrar;
    EditText txtNombres, txtApellidos, txtEmail, txtFechaNac,txtContrasenia, txtContrasenia2;
    Boolean esNuevoRegistro = true;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        cargarWidgets();
        inicializarFirebase();
        cargarSpinnerBasico();
    }

    private void cargarWidgets() {
        spinner = findViewById(R.id.spGenero);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEmail = findViewById(R.id.txtEmail);
        txtFechaNac = findViewById(R.id.txtFechaNac);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtContrasenia2 = findViewById(R.id.txtContrasenia2);
        
        btnCancelar.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        
        btnRegistrar.setOnClickListener(view -> {
            if (validarDatos()){
                String mensaje = crearUsuarioFireStore();
                mostrarMensaje(mensaje);
            }
        });
    }

    private boolean registrarDatos(String uuid) {
        boolean registroExitoso = true;
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        String nombres = txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String email = txtEmail.getText().toString();
        String genero = spinner.getSelectedItem().toString();
        String fechaNac = txtFechaNac.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setUuid(uuid);
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setGenero(genero);
        usuario.setContrasenia(contrasenia);

        try {
            fecha = formatoSalida.parse(fechaNac);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        usuario.setFechaNac(fecha);
        try {
            reference.child("usuario").child(usuario.getUuid()).setValue(usuario);
        } catch (Exception e){
            registroExitoso = false;
            Log.d("Error en el registro: " , e.getMessage());
        }
        return registroExitoso;
    }

    private Boolean validarDatos() {

        boolean valida = true;

        String nombres = txtNombres.getText().toString();
        String apellidos = txtApellidos.getText().toString();
        String email = txtEmail.getText().toString();
        String genero = spinner.getSelectedItem().toString();
        String fechaNac = txtFechaNac.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();
        String contrasenia2 = txtContrasenia2.getText().toString();

        if (nombres.equals("")){
            txtNombres.setError("Los nombres son obligatorios");
            valida = false;
        }
        if (apellidos.equals("")){
            txtApellidos.setError("Los apellidos son obligatorios");
            valida = false;
        }
        if (email.equals("")){
            txtEmail.setError("El email es obligatorio");
            valida = false;
        }
        if (fechaNac.equals("")){
            txtFechaNac.setError("La fecha de nacimiento es obligatoria");
            valida = false;
        }
        if (contrasenia.equals("")){
            txtContrasenia.setError("La contraseña es obligatoria");
            valida = false;
        }
        if (contrasenia2.equals("")){
            txtContrasenia2.setError("La contraseña 2 es obligatoria");
            valida = false;
        }
        if (!contrasenia.equals(contrasenia2)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            valida = false;
        }
        return valida;
    }

    private void cargarSpinnerBasico() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.generos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private void mostrarMensaje(String mensaje){
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana.setTitle("Mensaje Informativo");
        ventana.setMessage(mensaje);
        ventana.setPositiveButton("Volver al login", (dialogInterface, i) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        ventana.create().show();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private String crearUsuarioFireStore(){
        String mensaje = "Usuario Registrado";
        String email = txtEmail.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        registrarDatos(firebaseAuth.getCurrentUser().getUid());
                    } else {
                        Toast.makeText(RegistroActivity.this, "No se pudo crear el usuario en Firebase", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e){
            mensaje = "Error en el registro de firebase " + e.getMessage();
        }
        return mensaje;
    }
    
}