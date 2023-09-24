package com.upc.appadopcion;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.appadopcion.databinding.ActivityNavegacionBinding;
import com.upc.appadopcion.model.Usuario;

import java.util.Optional;

public class NavegacionActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavegacionBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    TextView tvUsuarioLogueado, tvEmailLogueado;
    Usuario usuario;

    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavegacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavegacion.toolbar);
        binding.appBarNavegacion.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacion);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        cargarWidgets();
        inicializarFirebase();
        verificarDatosUsuarioLogueado();
    }

    private void cargarWidgets() {
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        tvEmailLogueado = header.findViewById(R.id.tvEmailLogueado);
        tvUsuarioLogueado = header.findViewById(R.id.tvUsuarioLogueado);
    }

    private void verificarDatosUsuarioLogueado() {
        Log.d("login" , "verificarDatosUsuarioLogueado");

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d("login" , "usuario logueado " + firebaseUser.getEmail() + " " + firebaseUser.getUid());
            mostrarUsuarioLogueado(firebaseUser.getUid());
        } else {
            Log.d("login" , "usuario no se encuentra logueado ");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacion, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navegacion);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void mostrarUsuarioLogueado(String uuidUsuario) {
        reference.child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = new Usuario();
                for (DataSnapshot item: snapshot.getChildren()){
                    Usuario usuarioFireBase = item.getValue(Usuario.class);
                    if (uuidUsuario.equals(usuarioFireBase.getUuid())){
                        usuario = usuarioFireBase;
                        tvUsuarioLogueado.setText(usuario.getNombres() +" "+ usuario.getApellidos());
                        tvEmailLogueado.setText(usuario.getEmail());
                        Log.d("datos" , usuario.getNombres());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}