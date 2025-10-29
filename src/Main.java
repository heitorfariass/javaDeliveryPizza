package javaDeliveryPizza.src;
import java.util.ArrayList;
import java.util.Scanner;

class HelpersMain{

    static String nomeProduto(Produto p){
        if(p instanceof Pizza){ Pizza pi=(Pizza)p; return "Pizza "+pi.getSabor()+" ("+pi.getTamanho()+")"; }
        if(p instanceof Bebida){ Bebida b=(Bebida)p; return "Bebida "+b.getNome()+" ("+b.getTamanho()+")"; }
        return "Produto";
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
                System.out.println("\nPergunta 1) Qual o ticket médio dos pedidos entregues no delivery?");
                System.out.println("Resposta: R$ "+Helpers.arred2(Metricas.ticketMedio(pedidos)));

                System.out.println("\nPergunta 2) Qual produto mais vendido em cada dia da semana?");
                Metricas.maisVendidoPorDia(pedidos);

                System.out.println("\nPergunta 3) Quais os três sabores de pizza mais pedidos?");
                Metricas.top3Sabores(pedidos);

                System.out.println("\nPergunta 4) Quantos pedidos foram cancelados e por qual motivo?");
                Metricas.cancelados(pedidos);

                System.out.println("\nPergunta 5) Qual a distância média das entregas e as estimativas de tempo e frete?");
                Metricas.distanciaTempoFrete(pedidos);

                System.out.println("\nPergunta 6) Qual a satisfação média dos clientes do delivery?");
                System.out.println("Resposta: "+Helpers.arred2(Metricas.satisfacao(pedidos))+" / 5");
            } else if(op==2){
                MetricasEventos.resumo(eventos);

                System.out.println("\nPergunta 7) Qual evento gerou a maior receita de buffet do restaurante?");
                MetricasEventos.topBuffet(eventos);

                System.out.println("\nPergunta 8) Qual é a taxa de ocupação dos eventos por dia da semana?");
                MetricasEventos.ocupacaoPorDia(eventos);
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
