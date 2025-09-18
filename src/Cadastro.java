package javaDeliveryPizza.src;
import java.util.ArrayList;
import java.util.Scanner;

public class Cadastro {
    private static int nextId = 1;

    public static void cadastrarPedido(Scanner sc, ArrayList<Pedido> pedidos){
        Pedido p = new Pedido(nextId); nextId++;

        System.out.println("\n--- Novo Pedido ---");
        System.out.print("Dia da semana (1=Seg..7=Dom): ");
        p.setDiaDaSemana(sc.nextInt());

        System.out.println("Status: 1-ENTREGUE  2-CANCELADO");
        int s = sc.nextInt();
        if (s==1){
            p.setStatus("ENTREGUE");
        }else{
            p.setStatus("CANCELADO");
        }

        if (p.getStatus().equals("CANCELADO")){
            p.setAvaliacao(0); p.setDistanciaKm(-1.0);
            pedidos.add(p);
            System.out.println("Pedido #" + p.getNumPedido() + " CANCELADO cadastrado.");
            return;
        }

        System.out.print("Distância (km) ou -1 para não informar: ");
        p.setDistanciaKm(sc.nextDouble());

        System.out.print("Avaliação (1..5) ou 0: ");
        p.setAvaliacao(sc.nextInt());

        int loop=1;
        while(loop==1){
            System.out.println("Item: 1-PIZZA  2-BEBIDA");
            int tipo=sc.nextInt();
            if (tipo==1){
                sc.nextLine();
                System.out.print("Sabor da pizza : ");
                String sabor=sc.nextLine();
                System.out.print("Preço: ");
                double preco=sc.nextDouble();
                sc.nextLine();
                System.out.print("Tamanho [M/G]: ");
                String t=sc.nextLine();
                Pizza pi = new Pizza(sabor,t,preco);
                System.out.print("Quantidade: ");
                int q=sc.nextInt();
                
                p.addItem(new ItemProduto(pi,q));

            } else if (tipo==2){
                sc.nextLine();
                System.out.print("Nome da bebida: ");
                String nome=sc.nextLine();
                System.out.print("Preço: ");
                double preco=sc.nextDouble();                
                System.out.print("Tamanho [Lata/1L]: ");
                sc.nextLine();
                String t=sc.nextLine();
                Bebida b = new Bebida(nome,t,preco);
                System.out.print("Quantidade: ");
                int q=sc.nextInt();

                p.addItem(new ItemProduto(b,q));

            } else {
                System.out.println("Tipo inválido.");
            }
            System.out.print("Adicionar outro item? 1=sim 0=não: ");
            loop=sc.nextInt();
        }
        pedidos.add(p);
        System.out.println("Pedido #"+p.getNumPedido()+" cadastrado com "+p.getItens().size()+" item(ns).");
    }
}