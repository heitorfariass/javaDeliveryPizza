package javaDeliveryPizza.src;

import java.util.ArrayList;

public class Evento {
    private int id;
    private String nome;
    private String tipo;
    private int diaSemana;
    private String status;
    private int capacidade;
    private int publicoReal;
    private int ingressosVendidos;
    private double precoIngresso;
    private ArrayList<ItemProduto> buffet;
    private int avaliacao;

    public Evento(int id){
        this.id = id;
        this.status = "AGENDADO";
        this.buffet = new ArrayList<ItemProduto>();
        this.publicoReal = 0;
        this.ingressosVendidos = 0;
        this.avaliacao = 0;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public int getDiaSemana(){
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana){
        this.diaSemana = diaSemana;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getCapacidade(){
        return capacidade;
    }

    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }

    public int getPublicoReal(){
        return publicoReal;
    }

    public void setPublicoReal(int publicoReal){
        if(publicoReal < 0){
            publicoReal = 0;
        }
        this.publicoReal = publicoReal;
    }

    public int getIngressosVendidos(){
        return ingressosVendidos;
    }

    public void setIngressosVendidos(int ingressosVendidos){
        if(ingressosVendidos < 0){
            ingressosVendidos = 0;
        }
        this.ingressosVendidos = ingressosVendidos;
        this.publicoReal = ingressosVendidos;
    }

    public double getPrecoIngresso(){
        return precoIngresso;
    }

    public void setPrecoIngresso(double precoIngresso){
        if(precoIngresso < 0){
            precoIngresso = 0;
        }
        this.precoIngresso = precoIngresso;
    }

    public int getAvaliacao(){
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao){
        if(avaliacao < 0){
            avaliacao = 0;
        }
        if(avaliacao > 5){
            avaliacao = 5;
        }
        this.avaliacao = avaliacao;
    }

    public ArrayList<ItemProduto> getBuffet(){
        return buffet;
    }

    public void adicionarItemBuffet(ItemProduto item){
        buffet.add(item);
    }

    public double receitaIngressos(){
        return precoIngresso * ingressosVendidos;
    }
}
