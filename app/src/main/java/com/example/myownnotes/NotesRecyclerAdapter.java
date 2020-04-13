package com.example.myownnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>{
    private List<MenuNote> notes;
    Context context;
    private OnNoteListener mOnNoteListener;

    public NotesRecyclerAdapter(Context context, List<MenuNote> notes, OnNoteListener mOnNoteListener){
        this.context = context;
        this.notes = notes;
        this.mOnNoteListener = mOnNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.note_item, parent, false);
        ViewHolder v = new ViewHolder(view, mOnNoteListener);

        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assunto.setText(notes.get(position).getAssunto());
        holder.descricao.setText(notes.get(position).getDescricao());
        holder.data.setText(String.valueOf(notes.get(position).getData()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView assunto;
        TextView descricao;
        TextView data;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.onNoteListener = onNoteListener;
            assunto = itemView.findViewById(R.id.assunto);
            descricao = itemView.findViewById(R.id.descricao);
            data = itemView.findViewById(R.id.data);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
