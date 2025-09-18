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
            System.out.println("#"+p.getNumPedido()+" | Dia "+p.getDiaSemana()+" | "+p.getStatus()+
                " | Dist="+(p.getDistanciaKm()<0?"-":p.getDistanciaKm()+"km")+
                " | Aval="+(p.getAvaliacao()==0?"-":(""+p.getAvaliacao())));
            for(int j=0;j<p.getItens().size();j++){
                ItemProduto it=p.getItens().get(j);
                System.out.println("   - "+nomeProduto(it.getProduto())+" x"+it.getQuantidade());
            }
        }
    }
}

public class Main {
    public static void main(String[] args){
        ArrayList<Pedido> pedidos=new ArrayList<Pedido>();
        Scanner sc=new Scanner(System.in);
        int op=-1;
        while(op!=0){
            System.out.println("\n=== PIZZARIA DELIVERY ===");
            System.out.println("1-Cadastrar pedido");
            System.out.println("2-Listar pedidos");
            System.out.println("3-Ticket médio");
            System.out.println("4-Produto mais vendido por dia");
            System.out.println("5-Top 3 sabores");
            System.out.println("6-Cancelados (total)");
            System.out.println("7-Distância/Tempo/Frete");
            System.out.println("8-Satisfação média");
            System.out.println("9-Relatório completo");
            System.out.println("0-Sair");
            System.out.print("Escolha: ");
            op=sc.nextInt();

            if(op==1) Cadastro.cadastrarPedido(sc,pedidos);
            else if(op==2) HelpersMain.listar(pedidos);
            else if(op==3) System.out.println("Ticket médio: R$ "+Helpers.arred2(Metricas.ticketMedio(pedidos)));
            else if(op==4) Metricas.maisVendidoPorDia(pedidos);
            else if(op==5) Metricas.top3Sabores(pedidos);
            else if(op==6) Metricas.cancelados(pedidos);
            else if(op==7) Metricas.distanciaTempoFrete(pedidos);
            else if(op==8) System.out.println("Satisfação média: "+Helpers.arred2(Metricas.satisfacao(pedidos)));
            else if(op==9){
                System.out.println("Ticket médio: R$ "+Helpers.arred2(Metricas.ticketMedio(pedidos)));
                Metricas.maisVendidoPorDia(pedidos);
                Metricas.top3Sabores(pedidos);
                Metricas.cancelados(pedidos);
                Metricas.distanciaTempoFrete(pedidos);
                System.out.println("Satisfação média: "+Helpers.arred2(Metricas.satisfacao(pedidos)));
            } else if(op==0) System.out.println("Encerrando...");
            else System.out.println("Opção inválida.");
        }
        sc.close();
    }
}
