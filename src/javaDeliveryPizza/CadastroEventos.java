package javaDeliveryPizza;

import java.util.ArrayList;
import java.util.Scanner;

public class CadastroEventos {
    private static int nextId = 1;

    public static void configurarProximoId(int proximo){
        if(proximo <= 0){
            proximo = 1;
        }
        nextId = proximo;
    }

    public static void cadastrarEvento(Scanner sc, ArrayList<Evento> eventos){
        Evento evento = new Evento(nextId);
        nextId++;

        System.out.println("\n--- Novo Evento ---");
        System.out.print("Nome do evento: ");
        String nome = EntradaDados.lerLinha(sc);
        evento.setNome(nome);

        System.out.print("Tipo (palestra, show, workshop...): ");
        evento.setTipo(EntradaDados.lerLinha(sc));

        System.out.print("Dia da semana (1=Seg..7=Dom): ");
        evento.setDiaSemana(EntradaDados.lerInteiro(sc));

        System.out.print("Capacidade máxima: ");
        evento.setCapacidade(EntradaDados.lerInteiro(sc));

        System.out.print("Preço do ingresso: ");
        evento.setPrecoIngresso(EntradaDados.lerDouble(sc));

        int addBuffet = 0;
        System.out.print("Adicionar itens de buffet do restaurante? 1=sim 0=não: ");
        addBuffet = EntradaDados.lerInteiro(sc);

        while(addBuffet == 1){
            System.out.println("Item de buffet: 1-PIZZA  2-BEBIDA");
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
                int qtd = EntradaDados.lerInteiro(sc);
                evento.adicionarItemBuffet(new ItemProduto(pizza, qtd));
            } else if(tipo == 2){
                System.out.print("Nome da bebida: ");
                String nomeBebida = EntradaDados.lerLinha(sc);
                System.out.print("Preço: ");
                double preco = EntradaDados.lerDouble(sc);
                System.out.print("Tamanho [Lata/1L]: ");
                String tamanho = EntradaDados.lerLinha(sc);
                Bebida bebida = new Bebida(nomeBebida, tamanho, preco);
                System.out.print("Quantidade: ");
                int qtd = EntradaDados.lerInteiro(sc);
                evento.adicionarItemBuffet(new ItemProduto(bebida, qtd));
            } else {
                System.out.println("Opção inválida.");
            }

            System.out.print("Adicionar outro item ao buffet? 1=sim 0=não: ");
            addBuffet = EntradaDados.lerInteiro(sc);
        }

        eventos.add(evento);
        System.out.println("Evento #" + evento.getId() + " cadastrado com sucesso.");
    }
}
