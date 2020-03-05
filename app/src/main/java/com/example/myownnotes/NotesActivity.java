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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    List<MenuNote> noteList = new ArrayList<>();
    ImageView addNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Calendar calendar = Calendar.getInstance();
        String data;
        data = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);


        noteList.add(new MenuNote("Depressão", "Dor", data));
        noteList.add(new MenuNote("Sofrimento", "Autismo não é doença", data));

        System.out.println(calendar.getTime());

        RecyclerView recycle = findViewById(R.id.notasCard);
        NotesRecyclerAdapter adapt = new NotesRecyclerAdapter(noteList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapt);

        addNota = findViewById(R.id.addNote);
        final Context context = this;

        addNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_nota);
            }
        });

    }
}
