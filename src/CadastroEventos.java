package javaDeliveryPizza.src;

import java.util.ArrayList;
import java.util.Scanner;

public class CadastroEventos {
    private static int nextId = 1;

    public static void cadastrarEvento(Scanner sc, ArrayList<Evento> eventos){
        Evento evento = new Evento(nextId);
        nextId++;

        System.out.println("\n--- Novo Evento ---");
        sc.nextLine();
        System.out.print("Nome do evento: ");
        evento.setNome(sc.nextLine());

        System.out.print("Tipo (palestra, show, workshop...): ");
        evento.setTipo(sc.nextLine());

        System.out.print("Dia da semana (1=Seg..7=Dom): ");
        evento.setDiaSemana(sc.nextInt());

        System.out.print("Capacidade máxima: ");
        evento.setCapacidade(sc.nextInt());

        System.out.print("Preço do ingresso: ");
        evento.setPrecoIngresso(sc.nextDouble());

        int addBuffet = 1;
        System.out.print("Adicionar itens de buffet do restaurante? 1=sim 0=não: ");
        addBuffet = sc.nextInt();

        while(addBuffet == 1){
            System.out.println("Item de buffet: 1-PIZZA  2-BEBIDA");
            int tipo = sc.nextInt();
            sc.nextLine();

            if(tipo == 1){
                System.out.print("Sabor da pizza: ");
                String sabor = sc.nextLine();
                System.out.print("Preço: ");
                double preco = sc.nextDouble();
                sc.nextLine();
                System.out.print("Tamanho [M/G]: ");
                String tamanho = sc.nextLine();
                Pizza pizza = new Pizza(sabor, tamanho, preco);
                System.out.print("Quantidade: ");
                int qtd = sc.nextInt();
                evento.adicionarItemBuffet(new ItemProduto(pizza, qtd));
            } else if(tipo == 2){
                System.out.print("Nome da bebida: ");
                String nome = sc.nextLine();
                System.out.print("Preço: ");
                double preco = sc.nextDouble();
                sc.nextLine();
                System.out.print("Tamanho [Lata/1L]: ");
                String tamanho = sc.nextLine();
                Bebida bebida = new Bebida(nome, tamanho, preco);
                System.out.print("Quantidade: ");
                int qtd = sc.nextInt();
                evento.adicionarItemBuffet(new ItemProduto(bebida, qtd));
            } else {
                System.out.println("Tipo inválido.");
            }

            System.out.print("Adicionar outro item ao buffet? 1=sim 0=não: ");
            addBuffet = sc.nextInt();
        }

        eventos.add(evento);
        System.out.println("Evento #" + evento.getId() + " cadastrado com sucesso.");
    }
}
