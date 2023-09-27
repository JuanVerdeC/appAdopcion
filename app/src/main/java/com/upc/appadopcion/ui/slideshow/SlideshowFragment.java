package com.upc.appadopcion.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upc.appadopcion.R;
import com.upc.appadopcion.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    float latitud, longitud;
    String titulo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapa);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                LatLng latLng1 = new LatLng(Float.parseFloat("-12.108255054797555"), Float.parseFloat("-77.02879570654018"));
                LatLng latLng2 = new LatLng(Float.parseFloat("-12.083805337636674"), Float.parseFloat("-77.01495805356825"));
                LatLng latLng3 = new LatLng(Float.parseFloat("-12.135906648987008"), Float.parseFloat("-76.99480848871438"));
                LatLng latLng4 = new LatLng(Float.parseFloat("-11.933819928752978"), Float.parseFloat("-77.05827656809866"));
                LatLng latLng5 = new LatLng(Float.parseFloat("-12.201175943367065"), Float.parseFloat("-76.95954155588369"));
                titulo = "Mascota disponible para adopcion";
                googleMap.addMarker(new MarkerOptions().position(latLng1).title(titulo));
                googleMap.addMarker(new MarkerOptions().position(latLng2).title(titulo));
                googleMap.addMarker(new MarkerOptions().position(latLng3).title(titulo));
                googleMap.addMarker(new MarkerOptions().position(latLng4).title(titulo));
                googleMap.addMarker(new MarkerOptions().position(latLng5).title(titulo));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 10));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng latLng) {
                        // When clicked on map
                        // Initialize marker options
                        MarkerOptions markerOptions=new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(latLng);
                        // Set title of marker
                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        // Remove all marker
                        googleMap.clear();
                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return root;
    }

}