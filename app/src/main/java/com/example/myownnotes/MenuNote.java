package com.example.myownnotes;

public class MenuNote {
    private String assunto;
    private String descricao;
    private String data;

    public MenuNote(String assunto, String descricao, String data){
        this.assunto = assunto;
        this.descricao = descricao;
        this.data = data;
    }

    public String getAssunto(){
        return assunto;
    }

    public String getDescricao(){
        return descricao;
    }

    public String getData(){
        return data;
    }

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
