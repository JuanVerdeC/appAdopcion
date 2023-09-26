package com.upc.appadopcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.appadopcion.model.Publicacion;

public class PublicacionDetalladaActivity extends AppCompatActivity {
    TextView PubDetNombre, PubDetEdad, PubDetDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion_detallada);
        asignarReferencias();
        mostrarDetalle();
    }

    private void mostrarDetalle() {
        PubDetNombre.setText(getIntent().getStringExtra("var_nombre"));
        PubDetEdad.setText(getIntent().getStringExtra("var_edad"));
        PubDetDescripcion.setText(getIntent().getStringExtra("var_descripcion"));
    }

    private void asignarReferencias() {
        PubDetNombre = findViewById(R.id.PubDetNombre);
        PubDetEdad = findViewById(R.id.PubDetEdad);
        PubDetDescripcion  = findViewById(R.id.PubDetDescripcion);
    }



}