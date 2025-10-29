package javaDeliveryPizza.src;
public class Bebida extends Produto{
    String nome;
    public Bebida(String nome, String tamanho, double preco) {
        super(preco, tamanho);
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}