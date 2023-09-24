package com.upc.appadopcion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    Button btnRegistrar, btnLogin;
    EditText txtEmailLogin, txtContraseniaLogin;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cargarWidgets();
        inicializarFirebase();
    }

    private void cargarWidgets() {
        btnRegistrar = findViewById(R.id.btnRegistrarme);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        txtContraseniaLogin = findViewById(R.id.txtContraseniaLogin);
        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            if (validarDatos()){
                login();
            }
        });
    }
    private Boolean validarDatos() {
        boolean valida = true;
        String email = txtEmailLogin.getText().toString();
        String contrasenia = txtContraseniaLogin.getText().toString();

        if (email.equals("")){
            txtEmailLogin.setError("El email es obligatorio");
            valida = false;
        }
        if (contrasenia.equals("")){
            txtContraseniaLogin.setError("La contraseña es obligatoria");
            valida = false;
        }

        return valida;
    }

    private void login() {
        String email = txtEmailLogin.getText().toString();
        String contrasenia = txtContraseniaLogin.getText().toString();

        progressDialog.setMessage("Autenticando el usuario");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, NavegacionActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "El email o la contraseña son incorrectas", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }
}