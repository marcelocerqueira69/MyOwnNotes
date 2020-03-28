package com.example.myownnotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {

    List<MenuNote> noteList = new ArrayList<>();
    ImageView addNota;
    Button cancelNota;
    Button confirmNota;
    String assunto;
    String descricao;
    String data;
    int id;
    Calendar calendar = Calendar.getInstance();
    DatabaseHelper db =  new DatabaseHelper(this);
    EditText addAssunto;
    EditText addDescricao;
    EditText editAssunto;
    EditText editDescricao;
    Button confirmEdit;
    Button deleteEdit;
    ImageView closeEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteList = db.getEntriesFromDb();

        RecyclerView recycle = findViewById(R.id.notasCard);
        final NotesRecyclerAdapter adapt = new NotesRecyclerAdapter(this, noteList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapt);


        data = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

        addNota = findViewById(R.id.addNote);
        final Context context = this;

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_nota);

        addNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        cancelNota = dialog.findViewById(R.id.cancel_note);

        cancelNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmNota = dialog.findViewById(R.id.confirm_note);

        confirmNota.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addAssunto = dialog.findViewById(R.id.addAssunto);
                addDescricao = dialog.findViewById(R.id.addDescricao);
                assunto = addAssunto.getText().toString();
                descricao = addDescricao.getText().toString();
                db.addNote(assunto, descricao, data);
                adapt.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onNoteClick(final int position) {
        final Context context = this;
        String descricao;
        String assunto;
        final int id;

        MenuNote note = noteList.get(position);


        id = note.getId();
        descricao = note.getDescricao();
        assunto = note.getAssunto();
        Toast.makeText(context,"You clicked on the item number " + position + "\nid: " + id + "\nAssunto: " + assunto + "\nDescrição: " + descricao, Toast.LENGTH_SHORT).show();


        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.edit_delete_nota);
        dialog.show();

        closeEdit = dialog.findViewById(R.id.close_edit);

        closeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editDescricao = dialog.findViewById(R.id.editDescricao);

        editDescricao.setText(descricao, TextView.BufferType.EDITABLE);

        editAssunto = dialog.findViewById(R.id.editAssunto);

        editAssunto.setText(assunto, TextView.BufferType.EDITABLE);

        deleteEdit = dialog.findViewById(R.id.delete_note);

        deleteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.deleteNote(id)){
                    dialog.dismiss();
                    Toast.makeText(context, "Nota eliminada", Toast.LENGTH_SHORT).show();
                }

            }
        });

        confirmEdit = dialog.findViewById(R.id.confirm_edit);
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.editNote(id, editAssunto.getText().toString(), editDescricao.getText().toString())){
                    dialog.dismiss();
                    Toast.makeText(context, "Nota Editada", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
