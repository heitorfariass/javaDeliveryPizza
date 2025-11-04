package javaDeliveryPizza.domain;

public abstract class Produto {
    private double preco;
    private String tamanho;

    protected Produto(double preco, String tamanho){
        this.preco = preco;
        this.tamanho = tamanho;
    }

    public double getPreco(){
        return this.preco;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public String getTamanho(){
        return this.tamanho;
    }

    public void setTamanho(String tamanho){
        this.tamanho = tamanho;
    }

    public abstract String nomeBase();

    public String descricao(){
        return nomeBase()+" ("+this.tamanho+")";
    }
}
