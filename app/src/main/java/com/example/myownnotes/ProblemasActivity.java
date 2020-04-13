package com.example.myownnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemasActivity extends AppCompatActivity implements ProblemsRecyclerAdapter.OnNoteListener {

    List<Ponto> pontos = new ArrayList<>();
    String id_user;
    int id_ponto;
    ImageView closeProblems;

    Dialog dialog;


    EditText editAssuntoPonto;
    EditText editDescricaoPonto;

    Button confirmEditPonto;
    Button confirmDeletePonto;
    ImageView closeEditPonto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemas);

        Bundle bundle = getIntent().getExtras();
        id_user = bundle.getString("id_user");


        closeProblems = findViewById(R.id.closeProblems);

        closeProblems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ProblemasActivity.this, MapActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("id_user", id_user);
                mapIntent.putExtras(bundle);

                startActivity(mapIntent);
                finish();
            }
        });


        getPointsFromId();

        RecyclerView recycler = findViewById(R.id.prolemasUser);
        ProblemsRecyclerAdapter adapt = new ProblemsRecyclerAdapter(pontos, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapt);


    }

    @Override
    public void onNoteClick(int position) {
        id_ponto = pontos.get(position).getId_ponto();
        String descricao;
        String assunto;

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_delete_ponto);
        dialog.show();


        assunto = pontos.get(position).getAssunto();
        descricao = pontos.get(position).getDescricao();

        editAssuntoPonto = dialog.findViewById(R.id.editAssuntoPonto);
        editDescricaoPonto = dialog.findViewById(R.id.editDescricaoPonto);

        editAssuntoPonto.setText(assunto, TextView.BufferType.EDITABLE);
        editDescricaoPonto.setText(descricao, TextView.BufferType.EDITABLE);

        confirmEditPonto = dialog.findViewById(R.id.confirm_edit_ponto);
        confirmDeletePonto = dialog.findViewById(R.id.confirm_delete_ponto);
        closeEditPonto = dialog.findViewById(R.id.cancel_edit_ponto);

        closeEditPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        confirmEditPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPoint();
            }
        });

        confirmDeletePonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePonto();
            }
        });
    }

    private void getPointsFromId() {
        String url = MySingleton.URL + "pontos/getPontosById/" + id_user;

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i=0; i <response.length();i++){
                                JSONObject obj = response.getJSONObject(i);
                                Ponto ponto = new Ponto(obj.getInt("id_ponto"), obj.getString("assunto"), obj.getString("descricao"),
                                        obj.getDouble("latitude"), obj.getDouble("longitude"), obj.getString("imagem"),
                                        obj.getInt("id_user"));

                                pontos.add(ponto);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.ws_connection_err, Toast.LENGTH_SHORT).show();
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

    private void deletePonto(){
        String url = MySingleton.URL + "pontos/deletePonto/" + id_ponto;

        StringRequest deleteResquest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String eliminado = "Eliminado";
                        Log.d("REGISTO", "onResponse: " +response.length());
                        try {
                            if(response.equals(eliminado)){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), R.string.ponto_eliminado, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), R.string.ponto_nao_eliminado, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.ws_connection_err, Toast.LENGTH_SHORT).show();
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
        };

        MySingleton.getInstance(this).addToRequestQueue(deleteResquest);
    }

    private void editPoint() {
        String url = MySingleton.URL + "pontos/update/" + id_ponto;
        final String updated = "updated";

        StringRequest postResquest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if(response.equals(updated)){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), R.string.ponto_atualizado, Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(getApplicationContext(), R.string.ponto_nao_atualizado, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), R.string.ws_connection_err, Toast.LENGTH_SHORT).show();
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

                parametros.put("assunto", editAssuntoPonto.getText().toString());
                parametros.put("descricao", editDescricaoPonto.getText().toString());


                return parametros;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(postResquest);
    }
}
