package javaDeliveryPizza;
import java.util.ArrayList;

public class Procedimentos {

    public static double valorTotal(Pedido p) {
        double soma = 0.0;
        for (int i = 0; i < p.itens.size(); i++) {
            ItemProduto it = p.itens.get(i);
            soma = soma + (it.produto.preco * it.quantidade);
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
        if (d == 1) return "Seg";
        if (d == 2) return "Ter";
        if (d == 3) return "Qua";
        if (d == 4) return "Qui";
        if (d == 5) return "Sex";
        if (d == 6) return "Sab";
        if (d == 7) return "Dom";
        return "?";
    }

    public static String nomeProduto(Produto p) {
        if (p instanceof Pizza) {
            Pizza pizza = (Pizza) p;
            return "Pizza " + pizza.sabor + " (" + pizza.tamanho + ")";
        } else if (p instanceof Bebida) {
            Bebida b = (Bebida) p;
            return "Bebida " + b.nome + " (" + b.tamanho + ")";
        }
        return "Produto";
    }
}