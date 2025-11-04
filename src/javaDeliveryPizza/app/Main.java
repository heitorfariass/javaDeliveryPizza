package javaDeliveryPizza.app;

import java.util.ArrayList;
import java.util.Scanner;

import javaDeliveryPizza.domain.Evento;
import javaDeliveryPizza.domain.Pedido;
import javaDeliveryPizza.service.Cadastro;
import javaDeliveryPizza.service.CadastroEventos;
import javaDeliveryPizza.service.GestaoEventos;
import javaDeliveryPizza.service.Relatorios;
import javaDeliveryPizza.util.EntradaDados;
import javaDeliveryPizza.util.ListagemUtils;

public class Main {
    private static boolean autenticarDono(Scanner sc){
        System.out.print("Senha do proprietário: ");
        String senha = EntradaDados.lerLinha(sc);
        if(senha.equals("123oi")){
            System.out.println("Acesso liberado.");
            return true;
        }
        System.out.println("Senha incorreta.");
        return false;
    }

    private static void menuFuncionario(Scanner sc, ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        int op=-1;
        while(op!=0){
            System.out.println("\n=== MENU FUNCIONÁRIO ===");
            System.out.println("1-Registrar venda do restaurante");
            System.out.println("2-Listar vendas do restaurante");
            System.out.println("3-Agendar evento");
            System.out.println("4-Listar eventos");
            System.out.println("5-Atualizar status de evento");
            System.out.println("6-Remover venda");
            System.out.println("7-Remover evento");
            System.out.println("0-Voltar");
            System.out.print("Escolha: ");
            op=EntradaDados.lerInteiro(sc);
            if(op==1){
                Cadastro.registrarVenda(sc,pedidos);
            } else if(op==2){
                ListagemUtils.listarPedidos(pedidos);
            } else if(op==3){
                CadastroEventos.cadastrarEvento(sc,eventos);
            } else if(op==4){
                ListagemUtils.listarEventos(eventos);
            } else if(op==5){
                GestaoEventos.atualizarEvento(sc,eventos);
            } else if(op==6){
                if(pedidos.size()==0){
                    System.out.println("Não há vendas cadastradas.");
                    continue;
                }
                ListagemUtils.listarPedidos(pedidos);
                System.out.print("Número da venda para remover: ");
                int numero = EntradaDados.lerInteiro(sc);
                if(Cadastro.removerVenda(pedidos, numero)){
                    System.out.println("Venda removida.");
                }else{
                    System.out.println("Venda não encontrada.");
                }
            } else if(op==7){
                if(eventos.size()==0){
                    System.out.println("Não há eventos cadastrados.");
                    continue;
                }
                ListagemUtils.listarEventos(eventos);
                System.out.print("ID do evento para remover: ");
                int id = EntradaDados.lerInteiro(sc);
                if(GestaoEventos.removerEvento(eventos,id)){
                    System.out.println("Evento removido.");
                }else{
                    System.out.println("Evento não encontrado.");
                }
            } else if(op==0){
                System.out.println("Retornando...");
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuPatrao(Scanner sc, ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        int op=-1;
        while(op!=0){
            System.out.println("\n=== MENU PROPRIETÁRIO ===");
            System.out.println("1-Relatório completo");
            System.out.println("2-Listar vendas");
            System.out.println("3-Listar eventos");
            System.out.println("0-Voltar");
            System.out.print("Escolha: ");
            op=EntradaDados.lerInteiro(sc);

            if(op==1){
                Relatorios.exibirRelatorioCompleto(pedidos, eventos);
            } else if(op==2){
                ListagemUtils.listarPedidos(pedidos);
            } else if(op==3){
                ListagemUtils.listarEventos(eventos);
            } else if(op==0){
                System.out.println("Retornando...");
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    public static void main(String[] args){
        ArrayList<Pedido> pedidos=new ArrayList<Pedido>();
        ArrayList<Evento> eventos=new ArrayList<Evento>();
        Scanner sc=new Scanner(System.in);
        int op=-1;
        while(op!=0){
            System.out.println("\n=== SISTEMA RESTAURANTE & EVENTOS ===");
            System.out.println("1-Entrar como proprietário");
            System.out.println("2-Entrar como funcionário");
            System.out.println("0-Sair");
            System.out.print("Escolha: ");
            op=EntradaDados.lerInteiro(sc);

            if(op==1){
                if(autenticarDono(sc)){
                    menuPatrao(sc,pedidos,eventos);
                }
            } else if(op==2){
                menuFuncionario(sc,pedidos,eventos);
            } else if(op==0){
                System.out.println("Encerrando...");
            } else {
                System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}
