package com.filomenadeveloper.myunheiro.ui.agendas;

public class Pedidos {

    private String userID;
    private String nome;
    private String data;
    private String hora;
    private String maos;
    private String pes;
    private String local;

    public Pedidos() {
    }

    public Pedidos(String userID, String nome, String data, String hora, String maos, String pes, String local) {
       this.userID = userID;
        this.nome = nome;
        this.data = data;
        this.hora = hora;
        this.maos = maos;
        this.pes = pes;
        this.local = local;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMaos() {
        return maos;
    }

    public void setMaos(String maos) {
        this.maos = maos;
    }

    public String getPes() {
        return pes;
    }

    public void setPes(String pes) {
        this.pes = pes;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}