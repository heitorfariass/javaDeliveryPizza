package javaDeliveryPizza;
import java.util.ArrayList;
import java.util.Scanner;

class HelpersMain{

    static String nomeProduto(Produto p){
        if(p==null){
            return "Produto";
        }
        return p.descricao();
    }

    static void listar(ArrayList<Pedido> pedidos){
        System.out.println("\n--- Pedidos ---");
        if(pedidos.size()==0){ System.out.println("(vazio)"); return; }
        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);
            System.out.println("#"+p.getNumPedido()+" | Dia "+Helpers.nomeDia(p.getDiaSemana())+" | "+p.getStatus()+
                " | Dist="+(p.getDistanciaKm()<0?"-":p.getDistanciaKm()+"km")+
                " | Aval="+(p.getAvaliacao()==0?"-":(""+p.getAvaliacao())));
            if(p.getStatus().equals("CANCELADO")){
                System.out.println("   Motivo: "+p.getMotivoCancelamento());
            }
            for(int j=0;j<p.getItens().size();j++){
                ItemProduto it=p.getItens().get(j);
                System.out.println("   - "+nomeProduto(it.getProduto())+" x"+it.getQuantidade());
            }
        }
    }
}

class HelpersEventos{
    static void listar(ArrayList<Evento> eventos){
        System.out.println("\n--- Eventos ---");
        if(eventos.size()==0){ System.out.println("(vazio)"); return; }
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            double valorBuffet = Helpers.totalBuffet(ev);
            System.out.println("#"+ev.getId()+" | "+ev.getNome()+" | "+ev.getTipo()+" | Dia "+Helpers.nomeDia(ev.getDiaSemana()));
            System.out.println("   Status: "+ev.getStatus()+" | Capacidade: "+ev.getCapacidade()+" | Público: "+ev.getPublicoReal());
            System.out.println("   Ingressos vendidos: "+ev.getIngressosVendidos()+" | Avaliação: "+(ev.getAvaliacao()==0?"-":ev.getAvaliacao()));
            if(valorBuffet>0){
                System.out.println("   Buffet (R$ "+Helpers.arred2(valorBuffet)+"): ");
                for(int j=0;j<ev.getBuffet().size();j++){
                    ItemProduto it = ev.getBuffet().get(j);
                    System.out.println("      - "+HelpersMain.nomeProduto(it.getProduto())+" x"+it.getQuantidade());
                }
            } else {
                System.out.println("   Buffet: —");
            }
        }
    }
}

public class Main {
    private static boolean autenticarDono(Scanner sc){
        System.out.print("Senha do proprietário: ");
        sc.nextLine();
        String senha = sc.nextLine();
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
            System.out.println("1-Cadastrar pedido delivery");
            System.out.println("2-Listar pedidos delivery");
            System.out.println("3-Agendar evento");
            System.out.println("4-Listar eventos");
            System.out.println("5-Atualizar status de evento");
            System.out.println("6-Remover pedido delivery");
            System.out.println("7-Remover evento");
            System.out.println("0-Voltar");
            System.out.print("Escolha: ");
            op=sc.nextInt();
            if(op==1){
                Cadastro.cadastrarPedido(sc,pedidos);
            } else if(op==2){
                HelpersMain.listar(pedidos);
            } else if(op==3){
                CadastroEventos.cadastrarEvento(sc,eventos);
            } else if(op==4){
                HelpersEventos.listar(eventos);
            } else if(op==5){
                GestaoEventos.atualizarEvento(sc,eventos);
            } else if(op==6){
                if(pedidos.size()==0){
                    System.out.println("Não há pedidos cadastrados.");
                    continue;
                }
                HelpersMain.listar(pedidos);
                System.out.print("Número do pedido para remover: ");
                int numero = sc.nextInt();
                if(Cadastro.removerPedido(pedidos, numero)){
                    System.out.println("Pedido removido.");
                }else{
                    System.out.println("Pedido não encontrado.");
                }
            } else if(op==7){
                if(eventos.size()==0){
                    System.out.println("Não há eventos cadastrados.");
                    continue;
                }
                HelpersEventos.listar(eventos);
                System.out.print("ID do evento para remover: ");
                int id = sc.nextInt();
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
            System.out.println("1-Relatórios do delivery");
            System.out.println("2-Relatórios de eventos");
            System.out.println("3-Relatório integrado");
            System.out.println("4-Listar pedidos");
            System.out.println("5-Listar eventos");
            System.out.println("0-Voltar");
            System.out.print("Escolha: ");
            op=sc.nextInt();

            if(op==1){
                System.out.println("\nPergunta 1) Qual o ticket médio combinado entre pedidos entregues e buffets de eventos realizados?");
                System.out.println("Resposta: R$ "+Helpers.arred2(MetricasIntegradas.ticketMedioIntegrado(pedidos, eventos)));

                System.out.println("\nPergunta 2) Qual produto do restaurante (delivery + eventos) mais consumido em cada dia da semana?");
                MetricasIntegradas.produtoMaisVendidoPorDiaIntegrado(pedidos, eventos);

                System.out.println("\nPergunta 3) Quais os três sabores de pizza mais servidos somando delivery e eventos?");
                MetricasIntegradas.topSaboresIntegrado(pedidos, eventos);

                System.out.println("\nPergunta 4) Em dias com eventos, quantos pedidos do delivery foram cancelados e quais motivos apareceram?");
                MetricasIntegradas.cancelamentosEmDiasComEvento(pedidos, eventos);

                System.out.println("\nPergunta 5) Qual a distância média das entregas realizadas em dias com evento e as estimativas de tempo/frete?");
                MetricasIntegradas.distanciaEmDiasDeEvento(pedidos, eventos);

                System.out.println("\nPergunta 6) Qual a satisfação média combinada entre clientes do delivery e participantes dos eventos?");
                System.out.println("Resposta: "+Helpers.arred2(MetricasIntegradas.satisfacaoIntegrada(pedidos, eventos))+" / 5");
            } else if(op==2){
                MetricasEventos.resumo(eventos);

                System.out.println("\nPergunta 7) Qual evento gerou a maior receita de buffet do restaurante?");
                MetricasEventos.topBuffet(eventos);

                System.out.println("\nPergunta 8) Como está a ocupação dos eventos e o volume de itens do restaurante (delivery + buffet) em cada dia da semana?");
                MetricasIntegradas.ocupacaoEVolume(pedidos, eventos);
            } else if(op==3){
                System.out.println("\nPergunta 9) Quanto o restaurante faturou ao combinar delivery e eventos?");
                MetricasEventos.relatorioIntegrado(pedidos,eventos);
            } else if(op==4){
                HelpersMain.listar(pedidos);
            } else if(op==5){
                HelpersEventos.listar(eventos);
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
            op=sc.nextInt();

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
