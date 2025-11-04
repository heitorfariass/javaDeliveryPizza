package javaDeliveryPizza.util;

import java.util.ArrayList;

import javaDeliveryPizza.domain.Bebida;
import javaDeliveryPizza.domain.ItemProduto;
import javaDeliveryPizza.domain.Pedido;
import javaDeliveryPizza.domain.Pizza;
import javaDeliveryPizza.domain.Produto;

public class Procedimentos {

    public static double valorTotal(Pedido p) {
        double soma = 0.0;
        for (int i = 0; i < p.getItens().size(); i++) {
            ItemProduto it = p.getItens().get(i);
            soma = soma + (it.getProduto().getPreco() * it.getQuantidade());
        }
        return soma;
    }

    public static int indice(ArrayList<String> lista, String chave) {
        for (int i = 0; i < lista.size(); i++){
            if (lista.get(i).equals(chave)){
                return i;
            }
        }
        return -1;
    }

    public static int indiceMaior(ArrayList<Integer> conts) {
        int idx = 0;
        for (int i = 1; i < conts.size(); i++){
            if (conts.get(i) > conts.get(idx)){
                idx = i;
            }
        }
        return idx;
    }

    public static String nomeDia(int d) {
        return AnalyticsHelper.nomeDia(d);
    }

    public static String nomeProduto(Produto p) {
        if (p instanceof Pizza) {
            Pizza pizza = (Pizza) p;
            return "Pizza " + pizza.getSabor() + " (" + pizza.getTamanho() + ")";
        } else if (p instanceof Bebida) {
            Bebida b = (Bebida) p;
            return "Bebida " + b.getNome() + " (" + b.getTamanho() + ")";
        }
        return "Produto";
    }
}
