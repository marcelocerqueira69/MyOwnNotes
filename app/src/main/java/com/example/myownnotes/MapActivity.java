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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton logout;
    public double latitude;
    public double longitude;
    String id_user;
    ImageView viewProblemas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        id_user = bundle.getString("id_user");

        viewProblemas = findViewById(R.id.viewProblems);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MapActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

        viewProblemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent problemasIntent = new Intent(MapActivity.this, ProblemasActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id_user", id_user);

                problemasIntent.putExtras(bundle);

                startActivity(problemasIntent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getPointsAndMark();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                Intent newPonto = new Intent(MapActivity.this, AddPonto.class);

                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putString("id_user", id_user);

                newPonto.putExtras(bundle);

                startActivity(newPonto);
                finish();

            }
        });
    }

    private void getPointsAndMark() {
        String url = MySingleton.URL + "pontos/getPontos";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Ponto> pontos = new ArrayList<>();

                        try {
                            for(int i=0; i <response.length();i++){
                                JSONObject obj = response.getJSONObject(i);
                                Ponto ponto = new Ponto(obj.getInt("id_user"), obj.getString("assunto"), obj.getString("descricao"),
                                        obj.getDouble("latitude"), obj.getDouble("longitude"), obj.getString("imagem"),
                                        obj.getInt("id_user"));

                                pontos.add(ponto);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.ws_connection_err, Toast.LENGTH_SHORT).show();
                        }

                        for(Ponto ponto: pontos){
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(ponto.getLatitude(), ponto.getLongitude()));
                            markerOptions.title(ponto.getAssunto() + "\n" + ponto.getDescricao());
                            mMap.addMarker(markerOptions);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        Log.d("ERRO1", "onErrorResponse1: " +error.getMessage());
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                                Log.d("ERRO2", "onErrorResponse2: " +e1.getMessage());
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                Log.d("ERRO3", "onErrorResponse3: " +e2.getMessage());
                            }
                        }
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(getRequest);
    }



}
