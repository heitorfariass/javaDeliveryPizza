package javaDeliveryPizza.src;
public class Main {
    public static void main(String[] args) {

        Cliente cliente1 = new Cliente("João Silva", 5.0);

        Pizza pizza1 = new Pizza("Calabresa", "Grande", 45.0);
        Pizza pizza2 = new Pizza("Frango com Catupiry", "Média", 38.0);

        Pedido pedido1 = new Pedido(1, cliente1);
        pedido1.adicionarPizza(pizza1);
        pedido1.adicionarPizza(pizza2);

        System.out.println(cliente1.exibirDados());

        System.out.println(pizza1.exibirDados());
        System.out.println(pizza2.exibirDados());

        System.out.println(pedido1.exibirDados());

        pedido1.cancelarPedido("Pedido demorou muito");
        System.out.println("\nApós cancelamento:");
        System.out.println(pedido1.exibirDados());
    }
}