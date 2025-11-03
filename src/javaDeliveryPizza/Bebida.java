package javaDeliveryPizza;
public class Bebida extends Produto{
    String nome;
    public Bebida(String nome, String tamanho, double preco) {
        super(preco, tamanho);
        this.nome = nome;
    }
    @Override
    public String nomeBase(){
        return "Bebida "+nome;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}