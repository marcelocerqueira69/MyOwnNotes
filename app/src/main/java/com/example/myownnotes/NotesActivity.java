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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    List<MenuNote> noteList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance().format(calendar.getTime());


        noteList.add(new MenuNote("Depressão", "Dor", calendar.getTime()));
        noteList.add(new MenuNote("Sofrimento", "Autismo não é doença", calendar.getTime()));

        RecyclerView recycle = findViewById(R.id.notasCard);
        NotesRecyclerAdapter adapt = new NotesRecyclerAdapter(noteList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapt);

    }
}
