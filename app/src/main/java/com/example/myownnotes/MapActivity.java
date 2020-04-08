package com.example.myownnotes;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FloatingActionButton logout;

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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                Toast.makeText(getApplicationContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
                mMap.addMarker(markerOptions);
            }
        });

        LatLng Viana = new LatLng(41.69323, -8.83287);
        mMap.addMarker(new MarkerOptions().position(Viana).title("Marker in Viana"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Viana));
    }

    /*private void markPoint() {
        String url = MySingleton.URL + "pontos/createPonto";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("REGISTO", "onResponse: " +response.length());
                        try {
                            if(response.equals(true)){
                                Toast.makeText(getApplicationContext(), "Ponto criado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Ponto não criado", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro na conexão com o WS", Toast.LENGTH_SHORT).show();
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
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();

                parametros.put("name", nome.getText().toString().trim());
                parametros.put("username", username.getText().toString());
                String pass = password.getText().toString().trim();

                parametros.put("password", pass);

                parametros.put("email", email.getText().toString().trim());

                return parametros;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(postResquest);
    }*/
}
