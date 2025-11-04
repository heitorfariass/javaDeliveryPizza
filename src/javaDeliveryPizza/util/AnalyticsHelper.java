package javaDeliveryPizza.util;

import java.util.ArrayList;

import javaDeliveryPizza.domain.Evento;
import javaDeliveryPizza.domain.ItemProduto;
import javaDeliveryPizza.domain.Pedido;

public final class AnalyticsHelper {
    private AnalyticsHelper(){
    }

    public static String nomeDia(int d){
        if(d==1) return"Seg";
        if(d==2) return"Ter";
        if(d==3) return"Qua";
        if(d==4) return"Qui";
        if(d==5) return"Sex";
        if(d==6) return"Sab";
        if(d==7) return"Dom";
        return "?";
    }

    public static double arred2(double v){
        return Math.round(v*100.0)/100.0;
    }

    public static int indice (ArrayList<String> ls,String chave){
        for(int i=0;i<ls.size();i++){
            if(ls.get(i).equals(chave)){
                return i;
            }
        }
        return -1;
    }

    public static int indiceMaior(ArrayList<Integer> ls){
        int k=0;
        for(int i=1;i<ls.size();i++){
            if(ls.get(i)>ls.get(k)){
                k=i;
            }
        }
        return k;
    }

    public static double valorTotal(Pedido p){
        double s=0;
        for(int i=0; i<p.getItens().size(); i++){
            ItemProduto it=p.getItens().get(i);
            s+= it.getProduto().getPreco() * it.getQuantidade();
        }
        return s;
    }

    public static double totalBuffet(Evento evento){
        double soma = 0.0;
        for(int i=0;i<evento.getBuffet().size();i++){
            ItemProduto it = evento.getBuffet().get(i);
            soma += it.getProduto().getPreco() * it.getQuantidade();
        }
        return soma;
    }
}
