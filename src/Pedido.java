package javaDeliveryPizza.src;
import java.util.ArrayList;

public class Pedido{
    int numPedido;
    int diaSemana;
    String status;
    int avaliacao;
    double distanciaKm;
    ArrayList<ItemProduto> itens = new ArrayList<ItemProduto>();

    public Pedido (int numPedido){
        this.numPedido = numPedido;
    }
    public int getNumPedido(){
        return numPedido;
    }
    public void setNumPedido(int numPedido){
        this.numPedido=numPedido;
    }
    public int getDiaSemana(){
        return diaSemana;
    }
    public void setDiaDaSemana(int diaSemana){
        this.diaSemana = diaSemana;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public int getAvaliacao(){
        return avaliacao;
    }
    public void setAvaliacao(int avaliacao){
        this.avaliacao=avaliacao;
    }
    public double getDistanciaKm(){
        return distanciaKm;
    }
    public void setDistanciaKm(double d){
        distanciaKm=d;
    }
    public ArrayList<ItemProduto> getItens(){
        return itens;
    }
    public void addItem(ItemProduto it){
        itens.add(it);
    }
}