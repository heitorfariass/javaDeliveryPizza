import java.util.ArrayList;

class Cliente{
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
class Pizza{
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
class Pedido{
    int numPedido;
    Cliente cliente;
    ArrayList<Pizza> pizzas;
    double valorTotal;
    String status;
    String motivoCancelamento;
    public Pedido(int numPedido, Cliente cliente){
        this.numPedido = numPedido;
        this.cliente = cliente;
        this.pizzas = new ArrayList<>();
        this.valorTotal = 0.0;
        this.status = "Ativo";
        this.motivoCancelamento = "";
    }
    public int getNumPedido() {
        return numPedido;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public String getStatus() {
        return status;
    }
    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }
    public void adicionarPizza(Pizza pizza) {
        pizzas.add(pizza);
        valorTotal += pizza.getPreco();
    }
    public void cancelarPedido(String motivo) {
        this.status = "Cancelado";
        this.motivoCancelamento = motivo;
    }
    public String exibirDados() {
        String dados = "Pedido Numero: " + numPedido + " - Cliente: " + cliente.getNome() +
                       "\nStatus: " + status +
                       "\nValor total: R$ " + valorTotal;
        if (status == ("Cancelado")) {
            dados += "\nMotivo: " + motivoCancelamento;
        }
        return dados;
    }
}
public class Main {
    public static void main(String[] args) {
        // Criando um cliente
        Cliente cliente1 = new Cliente("João Silva", 5.0);

        // Criando algumas pizzas
        Pizza pizza1 = new Pizza("Calabresa", "Grande", 45.0);
        Pizza pizza2 = new Pizza("Frango com Catupiry", "Média", 38.0);

        // Criando um pedido
        Pedido pedido1 = new Pedido(1, cliente1);
        pedido1.adicionarPizza(pizza1);
        pedido1.adicionarPizza(pizza2);

        // Exibindo informações do cliente
        System.out.println(cliente1.exibirDados());

        // Exibindo as pizzas
        System.out.println(pizza1.exibirDados());
        System.out.println(pizza2.exibirDados());

        // Exibindo informações do pedido
        System.out.println(pedido1.exibirDados());

        // Cancelando o pedido
        pedido1.cancelarPedido("Pedido demorou muito");
        System.out.println("\nApós cancelamento:");
        System.out.println(pedido1.exibirDados());
    }
}