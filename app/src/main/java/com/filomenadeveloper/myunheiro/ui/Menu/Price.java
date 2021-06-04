package com.filomenadeveloper.myunheiro.ui.Menu;

public class Price {
    String produto;
    float price;

    public Price(String produto, float price) {
        this.produto = produto;
        this.price = price;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
