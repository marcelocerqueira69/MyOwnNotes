package com.example.myownnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView anonymous;
    TextView regist;
    EditText email;
    EditText password;
    CardView loginButton;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regist = findViewById(R.id.regist);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("") && email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.err_email_password, Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString().equals("") && email.getText().toString() != ("")){
                    Toast.makeText(getApplicationContext(), R.string.err_password, Toast.LENGTH_SHORT).show();
                }else if (password.getText().toString() != ("") && email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), R.string.err_email, Toast.LENGTH_SHORT).show();
                }else{
                    login();
                }

            }
        });


            regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registIntent = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(registIntent);
            }
        });



        anonymous = findViewById(R.id.anonymous);

        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notesIntent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(notesIntent);
            }
        });
    }

    private void login() {
        String url = MySingleton.URL + "user/login";


        StringRequest postResquest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JWT jwt = new JWT(response);
                            Claim subscriptionMetaData = jwt.getClaim("email");
                            String parsedValue = subscriptionMetaData.asString();

                            Claim subscriptionMetaData2 = jwt.getClaim("id_user");
                            String parsedValue2 = subscriptionMetaData2.asString();

                            Log.d("Email: ", parsedValue);
                            Log.d("ID do utilizador: ", parsedValue2);
                            id_user = parsedValue2;

                            if (email.getText().toString().equals(parsedValue)){
                                Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("id_user", id_user);
                                mapIntent.putExtras(bundle);
                                
                                startActivity(mapIntent);
                                Toast.makeText(getApplicationContext(), R.string.success_login, Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), R.string.email_err, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.passwd_incorreta, Toast.LENGTH_SHORT).show();
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

                String pass = password.getText().toString().trim();

                parametros.put("password", pass);

                parametros.put("email", email.getText().toString().trim());

                return parametros;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(postResquest);
    }



}


