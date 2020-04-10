package com.example.myownnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddPonto extends AppCompatActivity {

    ImageView takenPicture;
    CardView takePicture;
    EditText assunto;
    EditText descricao;
    Button confirmPonto;
    Button cancelPonto;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ponto);

        takenPicture = findViewById(R.id.takenPicture);
        takePicture = findViewById(R.id.takePicture);
        assunto = findViewById(R.id.assuntoPonto);
        descricao = findViewById(R.id.descricaoPonto);
        confirmPonto = findViewById(R.id.confirm_ponto);
        cancelPonto = findViewById(R.id.cancel_ponto);
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");



        if (ContextCompat.checkSelfPermission(AddPonto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPonto.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        cancelPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100){
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            takenPicture.setImageBitmap(captureImage);
        }
    }

    private void markPoint() {
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

                parametros.put("assunto", assunto.getText().toString().trim());
                parametros.put("descricao", descricao.getText().toString().trim());
                //parametros.put("latitude", latitude);

                return parametros;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(postResquest);
    }
}
