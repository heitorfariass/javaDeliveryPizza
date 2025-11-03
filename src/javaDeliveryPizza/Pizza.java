package javaDeliveryPizza;
public class Pizza extends Produto{
    String sabor;
    public Pizza(String sabor, String tamanho, double preco) {
        super(preco, tamanho);
        this.sabor = sabor;
        this.preco = preco;
    }
    @Override
    public String nomeBase(){
        return "Pizza "+sabor;
    }
    public String getSabor() {
        return sabor;
    }
    public void setSabor(String sabor) {
        this.sabor = sabor;
    }
}