package br.com.unifatecie.rmp;

public class ItemLista {
    private String nome;
    private String data;
    private int qtd;

    public ItemLista(String nome, String data, int qtd){
        this.nome = nome;
        this.data = data;
        this.qtd = qtd;
    }

    public String getNome(){
        return nome;
    }

    public String getData(){
        return data;
    }

    public int getQtd(){
        return qtd;
    }

    public void setNome(String nome) { this.nome = nome; }
    public void setData(String data) { this.data = data; }
    public void setQtd(int qtd) { this.qtd = qtd; }
}
