package com.example.myownnotes;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProblemsRecyclerAdapter extends RecyclerView.Adapter<ProblemsRecyclerAdapter.ViewHolder> {
    private List<Ponto> pontos;
    Context context;
    private OnNoteListener mOnNoteListener;

    public ProblemsRecyclerAdapter(List<Ponto> pontos, Context context, OnNoteListener onNoteListener){
        this.pontos = pontos;
        this.context = context;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ProblemsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.problem_item, parent, false);
        ViewHolder v = new ViewHolder(view, mOnNoteListener);

        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemsRecyclerAdapter.ViewHolder holder, int position) {
        String nomeimagem = pontos.get(position).getImagem();

        holder.assunto.setText(pontos.get(position).getAssunto());
        holder.descricao.setText(pontos.get(position).getDescricao());
        Picasso.with(context).load(MySingleton.URL + nomeimagem).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView assunto;
        TextView descricao;

        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            image = itemView.findViewById(R.id.picture);
            assunto = itemView.findViewById(R.id.assuntoProblem);
            descricao = itemView.findViewById(R.id.descricaoProblem);
            this.onNoteListener = onNoteListener;

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
