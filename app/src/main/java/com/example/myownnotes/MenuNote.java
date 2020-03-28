package com.example.myownnotes;

public class MenuNote {
    private int id;
    private String assunto;
    private String descricao;
    private String data;

    public MenuNote(int id, String assunto, String descricao, String data){
        this.id = id;
        this.assunto = assunto;
        this.descricao = descricao;
        this.data = data;
    }

    public MenuNote(){

    }

    public int getId(){ return id; }

    public String getAssunto(){
        return assunto;
    }

    public String getDescricao(){
        return descricao;
    }

    public String getData(){
        return data;
    }

    public void setId(int id){ this.id = id; }

    public void setAssunto(String assunto){
        this.assunto = assunto;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public void setData(String data){
        this.data = data;
    }
}
