package javaDeliveryPizza.src;
public class Pizza{
    double preco;
    String tamanho;
    String sabor;
     public Pizza(String sabor, String tamanho, double preco) {
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.preco = preco;
    }
    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }
    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String exibirDados() {
        return "Pizza: " + sabor + " (" + tamanho + ") - R$ " + preco;
    }
}