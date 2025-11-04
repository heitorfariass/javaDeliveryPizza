package javaDeliveryPizza.service;

import java.util.ArrayList;
import java.util.Scanner;

import javaDeliveryPizza.domain.Bebida;
import javaDeliveryPizza.domain.ItemProduto;
import javaDeliveryPizza.domain.Pedido;
import javaDeliveryPizza.domain.Pizza;
import javaDeliveryPizza.util.EntradaDados;

public class Cadastro {
    private static int nextId = 1;

    public static void configurarProximoId(int proximo){
        if(proximo <= 0){
            proximo = 1;
        }
        nextId = proximo;
    }

    public static void registrarVenda(Scanner sc, ArrayList<Pedido> pedidos){
        Pedido pedido = new Pedido(nextId);
        nextId++;

        System.out.println("\n--- Nova venda do restaurante ---");
        System.out.print("Dia da semana (1=Seg..7=Dom): ");
        pedido.setDiaDaSemana(EntradaDados.lerInteiro(sc));

        System.out.println("Status: 1-SERVIDO  2-CANCELADO");
        int status = EntradaDados.lerInteiro(sc);
        if(status == 1){
            pedido.setStatus("SERVIDO");
        } else {
            pedido.setStatus("CANCELADO");
        }

        if(pedido.getStatus().equals("CANCELADO")){
            System.out.print("Motivo do cancelamento: ");
            String motivo = EntradaDados.lerLinha(sc).trim();
            if(motivo.length() == 0){
                motivo = "Não informado";
            }
            pedido.setMotivoCancelamento(motivo);
            pedido.setAvaliacao(0);
            pedidos.add(pedido);
            System.out.println("Venda #" + pedido.getNumPedido() + " CANCELADA registrada.");
            return;
        }

        System.out.print("Avaliação (1..5) ou 0 para não informar: ");
        pedido.setAvaliacao(EntradaDados.lerInteiro(sc));

        int adicionar = 1;
        while(adicionar == 1){
            System.out.println("Item consumido: 1-PIZZA  2-BEBIDA");
            int tipo = EntradaDados.lerInteiro(sc);

            if(tipo == 1){
                System.out.print("Sabor da pizza: ");
                String sabor = EntradaDados.lerLinha(sc);
                System.out.print("Preço: ");
                double preco = EntradaDados.lerDouble(sc);
                System.out.print("Tamanho [M/G]: ");
                String tamanho = EntradaDados.lerLinha(sc);
                Pizza pizza = new Pizza(sabor, tamanho, preco);
                System.out.print("Quantidade: ");
                int quantidade = EntradaDados.lerInteiro(sc);
                pedido.addItem(new ItemProduto(pizza, quantidade));
            } else if(tipo == 2){
                System.out.print("Nome da bebida: ");
                String nome = EntradaDados.lerLinha(sc);
                System.out.print("Preço: ");
                double preco = EntradaDados.lerDouble(sc);
                System.out.print("Tamanho [Lata/1L]: ");
                String tamanho = EntradaDados.lerLinha(sc);
                Bebida bebida = new Bebida(nome, tamanho, preco);
                System.out.print("Quantidade: ");
                int quantidade = EntradaDados.lerInteiro(sc);
                pedido.addItem(new ItemProduto(bebida, quantidade));
            } else {
                System.out.println("Opção inválida.");
            }

            System.out.print("Adicionar outro item? 1=sim 0=não: ");
            adicionar = EntradaDados.lerInteiro(sc);
        }

        pedidos.add(pedido);
        System.out.println("Venda #" + pedido.getNumPedido() + " registrada com " + pedido.getItens().size() + " item(ns).");
    }

    public static boolean removerVenda(ArrayList<Pedido> pedidos, int numero){
        for(int i=0;i<pedidos.size();i++){
            if(pedidos.get(i).getNumPedido() == numero){
                pedidos.remove(i);
                return true;
            }
        }
        return false;
    }
}
