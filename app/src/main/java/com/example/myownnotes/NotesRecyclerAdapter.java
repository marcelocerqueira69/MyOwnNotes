package com.example.myownnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>{
    private List<MenuNote> notes;

    public NotesRecyclerAdapter(List<MenuNote> notes){
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.note_item, parent, false);
        ViewHolder v = new ViewHolder(view);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assunto.setText(notes.get(position).getAssunto());
        holder.descricao.setText(notes.get(position).getDescricao());
        holder.data.setText(String.valueOf(notes.get(position).getData()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView assunto;
        TextView descricao;
        TextView data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assunto = itemView.findViewById(R.id.assunto);
            descricao = itemView.findViewById(R.id.descricao);
            data = itemView.findViewById(R.id.data);

        }
    }
}
