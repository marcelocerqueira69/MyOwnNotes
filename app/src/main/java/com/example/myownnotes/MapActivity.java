package com.example.myownnotes;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton logout;
    public double latitude;
    public double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                Intent newPonto = new Intent(MapActivity.this, AddPonto.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                newPonto.putExtras(bundle);
                startActivity(newPonto);
                Toast.makeText(getApplicationContext(), latLng.toString(), Toast.LENGTH_SHORT).show();


                /*MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                Toast.makeText(getApplicationContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
                mMap.addMarker(markerOptions);*/
            }
        });

        LatLng Viana = new LatLng(41.69323, -8.83287);
        mMap.addMarker(new MarkerOptions().position(Viana).title("Marker in Viana"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Viana));
    }



}
