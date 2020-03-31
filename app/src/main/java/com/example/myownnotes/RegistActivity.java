package com.example.myownnotes;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {
    EditText nome;
    EditText username;
    EditText email;
    EditText password;
    EditText confirmpassowrd;
    CardView registar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        nome = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassowrd = findViewById(R.id.confirm_password);
        registar = findViewById(R.id.registButton);

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(confirmpassowrd.getText().toString())){
                    registar();
                }else{
                    Toast.makeText(getApplicationContext(), "As passwords não coincidem!", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void registar() {
        String url = MySingleton.URL + "user/registar";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("REGISTO", "onResponse: " +response.length());
                        try {

                            if(response.length() == 15 ) {
                                Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Registado com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Utilizador já existe no sistema", Toast.LENGTH_SHORT).show();

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
    }


    }
