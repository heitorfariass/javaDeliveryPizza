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
        System.out.println("\n--- Vendas do restaurante ---");
        if(pedidos.size()==0){ System.out.println("(vazio)"); return; }
        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);
            double valor = Helpers.arred2(Helpers.valorTotal(p));
            System.out.println("#"+p.getNumPedido()+" | Dia "+Helpers.nomeDia(p.getDiaSemana())+" | "+p.getStatus()+
                " | Aval="+(p.getAvaliacao()==0?"-":(""+p.getAvaliacao()))+
                " | Total=R$ "+valor);
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
                HelpersMain.listar(pedidos);
            } else if(op==3){
                CadastroEventos.cadastrarEvento(sc,eventos);
            } else if(op==4){
                HelpersEventos.listar(eventos);
            } else if(op==5){
                GestaoEventos.atualizarEvento(sc,eventos);
            } else if(op==6){
                if(pedidos.size()==0){
                    System.out.println("Não há vendas cadastradas.");
                    continue;
                }
                HelpersMain.listar(pedidos);
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
                HelpersEventos.listar(eventos);
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
                HelpersMain.listar(pedidos);
            } else if(op==3){
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
