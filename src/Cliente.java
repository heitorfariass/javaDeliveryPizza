package javaDeliveryPizza.src;
public class Cliente{
    String nome;
    double distanciaKm;
    public Cliente (String nome, double distanciaKm){
        this.nome = nome;
        this.distanciaKm = distanciaKm;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public double getDistancia(){
        return distanciaKm;
    }
    public void setDistancia(double distanciaKm){
        this.distanciaKm = distanciaKm;
    }
    public String exibirDados(){
        return "Nome: " + nome + ", Distancia: " + distanciaKm + "km";
    }
}