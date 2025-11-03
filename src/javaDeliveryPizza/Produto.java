package javaDeliveryPizza;

public abstract class Produto {
    double preco;
    String tamanho;
    public Produto (double preco, String tamanho){
        this.preco = preco;
        this.tamanho = tamanho;
    }
    double getPreco(){
        return this.preco;
    }
    void setPreco(double preco){
        this.preco = preco;
    }
    String getTamanho(){
        return this.tamanho;
    }
    void setTamanho(String tamanho){
        this.tamanho = tamanho;
    }

    public abstract String nomeBase();

    public String descricao(){
        return nomeBase()+" ("+this.tamanho+")";
    }
}
class ItemProduto {
    Produto produto;
    int quantidade;
    public ItemProduto(Produto produto,int quantidade){
        this.produto=produto;
        this.quantidade=quantidade;
    }
    public Produto getProduto(){
        return produto;
    }
    public void setProduto(Produto p){
        produto=p;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public void setQuantidade(int q){
        quantidade=q;
    }
}