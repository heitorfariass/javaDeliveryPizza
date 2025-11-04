package javaDeliveryPizza.util;

import java.util.ArrayList;

import javaDeliveryPizza.domain.Evento;
import javaDeliveryPizza.domain.ItemProduto;
import javaDeliveryPizza.domain.Pedido;
import javaDeliveryPizza.domain.Produto;

public final class ListagemUtils {
    private ListagemUtils(){
    }

    public static String nomeProduto(Produto p){
        if(p==null){
            return "Produto";
        }
        return p.descricao();
    }

    public static void listarPedidos(ArrayList<Pedido> pedidos){
        System.out.println("\n--- Vendas do restaurante ---");
        if(pedidos.size()==0){ System.out.println("(vazio)"); return; }
        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);
            double valor = AnalyticsHelper.arred2(AnalyticsHelper.valorTotal(p));
            System.out.println("#"+p.getNumPedido()+" | Dia "+AnalyticsHelper.nomeDia(p.getDiaSemana())+" | "+p.getStatus()+
                " | Aval="+(p.getAvaliacao()==0?"-":(""+p.getAvaliacao()))+
                " | Total=R$ "+valor);
            if("CANCELADO".equals(p.getStatus())){
                System.out.println("   Motivo: "+p.getMotivoCancelamento());
            }
            for(int j=0;j<p.getItens().size();j++){
                ItemProduto it=p.getItens().get(j);
                System.out.println("   - "+nomeProduto(it.getProduto())+" x"+it.getQuantidade());
            }
        }
    }

    public static void listarEventos(ArrayList<Evento> eventos){
        System.out.println("\n--- Eventos ---");
        if(eventos.size()==0){ System.out.println("(vazio)"); return; }
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            double valorBuffet = AnalyticsHelper.totalBuffet(ev);
            System.out.println("#"+ev.getId()+" | "+ev.getNome()+" | "+ev.getTipo()+" | Dia "+AnalyticsHelper.nomeDia(ev.getDiaSemana()));
            System.out.println("   Status: "+ev.getStatus()+" | Capacidade: "+ev.getCapacidade()+" | Público: "+ev.getPublicoReal());
            System.out.println("   Ingressos vendidos: "+ev.getIngressosVendidos()+" | Avaliação: "+(ev.getAvaliacao()==0?"-":ev.getAvaliacao()));
            if(valorBuffet>0){
                System.out.println("   Buffet (R$ "+AnalyticsHelper.arred2(valorBuffet)+"): ");
                for(int j=0;j<ev.getBuffet().size();j++){
                    ItemProduto it = ev.getBuffet().get(j);
                    System.out.println("      - "+nomeProduto(it.getProduto())+" x"+it.getQuantidade());
                }
            } else {
                System.out.println("   Buffet: —");
            }
        }
    }
}
