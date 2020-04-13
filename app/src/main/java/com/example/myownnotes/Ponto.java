package com.example.myownnotes;

public class Ponto {

    private int id_ponto;
    private String assunto;
    private String descricao;
    private double latitude;
    private double longitude;
    private String imagem;
    private int id_user;

    public Ponto(int id_ponto, String assunto, String desricao, double latitude, double longitude, String imagem, int id_user){
        this.id_ponto = id_ponto;
        this.assunto = assunto;
        this.descricao = desricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagem = imagem;
        this.id_user = id_user;
    }

    public Ponto(){}

    public int getId_ponto() {
        return id_ponto;
    }

    public void setId_ponto(int id_ponto) {
        this.id_ponto = id_ponto;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
