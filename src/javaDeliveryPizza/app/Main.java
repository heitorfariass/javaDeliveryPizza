package javaDeliveryPizza.app;

import java.util.ArrayList;
import java.util.Scanner;

import javaDeliveryPizza.domain.Bebida;
import javaDeliveryPizza.domain.Evento;
import javaDeliveryPizza.domain.ItemProduto;
import javaDeliveryPizza.domain.Pedido;
import javaDeliveryPizza.domain.Pizza;
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

    private static void carregarDadosExemplo(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        if(!pedidos.isEmpty() || !eventos.isEmpty()){
            return;
        }

        String[] saboresPizza = {"Margherita", "Calabresa", "Frango com Catupiry", "Quatro Queijos", "Portuguesa", "Pepperoni"};
        String[] bebidas = {"Refrigerante Cola", "Suco de Laranja", "Água com Gás", "Chá Gelado", "Refrigerante Limão", "Suco de Uva"};
        String[] motivosCancelamento = {"Cliente desistiu", "Pagamento não aprovado", "Pedido duplicado"};

        int proximoPedidoId = 1;
        for(; proximoPedidoId <= 160; proximoPedidoId++){
            Pedido pedido = new Pedido(proximoPedidoId);
            pedido.setDiaDaSemana(((proximoPedidoId - 1) % 7) + 1);

            boolean servido = proximoPedidoId % 5 != 0;
            if(servido){
                pedido.setStatus("SERVIDO");
                int avaliacao = 2 + (proximoPedidoId % 4);
                if(avaliacao > 5){
                    avaliacao = 5;
                }
                pedido.setAvaliacao(avaliacao);

                String sabor = saboresPizza[proximoPedidoId % saboresPizza.length];
                String tamanhoPizza = (proximoPedidoId % 2 == 0) ? "G" : "M";
                double precoPizza = 28.0 + (proximoPedidoId % 5) * 2.5;
                int quantidadePizza = 1 + (proximoPedidoId % 3);
                pedido.addItem(new ItemProduto(new Pizza(sabor, tamanhoPizza, precoPizza), quantidadePizza));

                if(proximoPedidoId % 2 == 0){
                    String bebidaNome = bebidas[proximoPedidoId % bebidas.length];
                    String tamanhoBebida = (proximoPedidoId % 3 == 0) ? "1L" : "Lata";
                    double precoBebida = 5.0 + (proximoPedidoId % 4);
                    int quantidadeBebida = 1 + (proximoPedidoId % 2);
                    pedido.addItem(new ItemProduto(new Bebida(bebidaNome, tamanhoBebida, precoBebida), quantidadeBebida));
                }
            }else{
                pedido.setStatus("CANCELADO");
                pedido.setMotivoCancelamento(motivosCancelamento[proximoPedidoId % motivosCancelamento.length]);
                pedido.setAvaliacao(0);
            }

            pedidos.add(pedido);
        }
        Cadastro.configurarProximoId(proximoPedidoId);

        String[] tiposEvento = {"Show", "Aniversário", "Workshop", "Palestra", "Conferência", "Casamento"};
        int proximoEventoId = 1;
        for(; proximoEventoId <= 40; proximoEventoId++){
            Evento evento = new Evento(proximoEventoId);
            evento.setNome("Evento Modelo " + proximoEventoId);
            evento.setTipo(tiposEvento[proximoEventoId % tiposEvento.length]);
            evento.setDiaSemana(((proximoEventoId + 1) % 7) + 1);
            int capacidade = 60 + (proximoEventoId % 6) * 15;
            evento.setCapacidade(capacidade);
            double precoIngresso = 40.0 + (proximoEventoId % 5) * 5.0;
            evento.setPrecoIngresso(precoIngresso);

            String status;
            if(proximoEventoId % 6 == 0){
                status = "CANCELADO";
            }else if(proximoEventoId % 5 == 0){
                status = "AGENDADO";
            }else{
                status = "REALIZADO";
            }
            evento.setStatus(status);

            boolean buffetContratado = proximoEventoId % 4 != 0;
            if(buffetContratado){
                String saborBuffet = saboresPizza[proximoEventoId % saboresPizza.length];
                double precoPizzaBuffet = 29.0 + (proximoEventoId % 4) * 3.0;
                int quantidadePizzaBuffet = 10 + (proximoEventoId % 6) * 2;
                evento.adicionarItemBuffet(new ItemProduto(new Pizza(saborBuffet, "G", precoPizzaBuffet), quantidadePizzaBuffet));

                if(proximoEventoId % 2 == 0){
                    String bebidaBuffet = bebidas[(proximoEventoId + 1) % bebidas.length];
                    double precoBebidaBuffet = 4.5 + (proximoEventoId % 5);
                    int quantidadeBebidaBuffet = 12 + (proximoEventoId % 5) * 3;
                    evento.adicionarItemBuffet(new ItemProduto(new Bebida(bebidaBuffet, "1L", precoBebidaBuffet), quantidadeBebidaBuffet));
                }
            }

            if("REALIZADO".equals(status)){
                int ingressosVendidos = Math.min(capacidade, 40 + (proximoEventoId % 25));
                evento.setIngressosVendidos(ingressosVendidos);
                int publicoReal = ingressosVendidos - (proximoEventoId % 4);
                evento.setPublicoReal(publicoReal);
                int avaliacao = 3 + (proximoEventoId % 3);
                if(avaliacao > 5){
                    avaliacao = 5;
                }
                evento.setAvaliacao(avaliacao);
            }else{
                evento.setIngressosVendidos(0);
                evento.setPublicoReal(0);
                evento.setAvaliacao(0);
            }

            eventos.add(evento);
        }
        CadastroEventos.configurarProximoId(proximoEventoId);
    }

    public static void main(String[] args){
        ArrayList<Pedido> pedidos=new ArrayList<Pedido>();
        ArrayList<Evento> eventos=new ArrayList<Evento>();
        carregarDadosExemplo(pedidos, eventos);
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
