package javaDeliveryPizza;

import java.util.ArrayList;

public class Pedido{
    int numPedido;
    int diaSemana;
    String status;
    int avaliacao;
    String motivoCancelamento = "";
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
    public String getMotivoCancelamento(){
        return motivoCancelamento;
    }
    public void setMotivoCancelamento(String motivo){
        this.motivoCancelamento = motivo;
    }
    public ArrayList<ItemProduto> getItens(){
        return itens;
    }
    public void addItem(ItemProduto it){
        itens.add(it);
    }
}
