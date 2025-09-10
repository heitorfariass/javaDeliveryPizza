package javaDeliveryPizza.src;
import java.util.ArrayList;

public class Pedido{
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