package com.upc.appadopcion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityListar extends AppCompatActivity {

    FloatingActionButton btnNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
    }

    private void asignarReferencias(){
        btnNuevo=findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(view -> {
            Intent intent= new Intent(this, Registrar_mascotaActivity.class);
            startActivity(intent);
        });
    }

}