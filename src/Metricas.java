package javaDeliveryPizza.src;
import java.util.ArrayList;

class Helpers{
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
        ArrayList<ItemProduto> itens = evento.getBuffet();
        for(int i=0;i<itens.size();i++){
            ItemProduto it = itens.get(i);
            soma += it.getProduto().getPreco() * it.getQuantidade();
        }
        return soma;
    }
}

public class Metricas {
    public static double ticketMedio(ArrayList<Pedido> pedidos){
        double soma=0; int qt=0;
        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);
            if(p.getStatus().equals("ENTREGUE")){
                soma+=Helpers.valorTotal(p); qt++;
            }
        }
        if (qt==0){
        return 0;
        }else{
            return soma / qt;
        }
    }
    public static void maisVendidoPorDia(java.util.ArrayList<Pedido> pedidos){
        System.out.println("\n--- Mais vendido por dia ---");

        for (int dia = 1; dia <= 7; dia++) {
            java.util.ArrayList<String> nomes = new java.util.ArrayList<String>();
            java.util.ArrayList<Integer> conts = new java.util.ArrayList<Integer>();

            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = pedidos.get(i);
                if (p.getStatus().equals("ENTREGUE") && p.getDiaSemana() == dia) {

                    for (int j = 0; j < p.getItens().size(); j++) {
                        ItemProduto it = p.getItens().get(j);

                        String nome;
                        Produto prod = it.getProduto();
                        if (prod instanceof Pizza) {
                            Pizza pi = (Pizza) prod;
                            nome = "Pizza " + pi.getSabor() + " (" + pi.getTamanho() + ")";
                        } else if (prod instanceof Bebida) {
                            Bebida b = (Bebida) prod;
                            nome = "Bebida " + b.getNome() + " (" + b.getTamanho() + ")";
                        } else {
                            nome = "Produto";
                        }

                        int idx = Helpers.indice(nomes, nome);
                        if (idx == -1) {
                            nomes.add(nome);
                            conts.add(it.getQuantidade());
                        } else {
                            conts.set(idx, conts.get(idx) + it.getQuantidade());
                        }
                    }
                }
            }
        if (nomes.size() == 0) {
            System.out.println(Helpers.nomeDia(dia) + ": —");
        } else {
            int k = Helpers.indiceMaior(conts);
            System.out.println(Helpers.nomeDia(dia) + ": " + nomes.get(k) + " (" + conts.get(k) + ")");
        }
        }
    }
    public static void top3Sabores(ArrayList<Pedido> pedidos){
        System.out.println("\n--- Top 3 sabores de pizza ---");
        ArrayList<String> sabores=new ArrayList<String>();
        ArrayList<Integer> conts=new ArrayList<Integer>();

        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);

            if(p.getStatus().equals("ENTREGUE")){
                for(int j=0;j<p.getItens().size();j++){
                    ItemProduto it=p.getItens().get(j);

                    if(it.getProduto() instanceof Pizza){
                        String s=((Pizza)it.getProduto()).getSabor();
                        int idx=Helpers.indice(sabores,s);
                        if(idx==-1){
                            sabores.add(s); conts.add(it.getQuantidade());
                        }else{
                            conts.set(idx, conts.get(idx)+it.getQuantidade());
                        }
                    }
                }
            }
        }
        for(int pos=1;pos<=3;pos++){
            if(sabores.size()==0){
                break;
            }
            int k=Helpers.indiceMaior(conts);
            System.out.println(pos+") "+sabores.get(k)+" — "+conts.get(k));
            sabores.remove(k); conts.remove(k); pos--;
            if(sabores.size()==0){
                break;
            }
        }
    }
    public static void cancelados(ArrayList<Pedido> pedidos){
        int total=0;
        ArrayList<String> motivos = new ArrayList<String>();
        ArrayList<Integer> contagem = new ArrayList<Integer>();

        for(int i=0;i<pedidos.size();i++){
            Pedido p = pedidos.get(i);
            if(p.getStatus().equals("CANCELADO")){
                total++;
                String motivo = p.getMotivoCancelamento();
                if(motivo==null || motivo.trim().length()==0){
                    motivo = "Não informado";
                }
                int idx = Helpers.indice(motivos, motivo);
                if(idx==-1){
                    motivos.add(motivo);
                    contagem.add(1);
                }else{
                    contagem.set(idx, contagem.get(idx)+1);
                }
            }
        }
        System.out.println("\n--- Cancelamentos ---\nTotal: "+total);
        if(total==0){
            return;
        }
        System.out.println("Ranking de motivos:");
        int pos = 1;
        while(motivos.size()>0){
            int idxMaior = Helpers.indiceMaior(contagem);
            System.out.println(pos+") "+motivos.get(idxMaior)+" — "+contagem.get(idxMaior));
            motivos.remove(idxMaior);
            contagem.remove(idxMaior);
            pos++;
        }
    }
    public static void distanciaTempoFrete(ArrayList<Pedido> pedidos){
        double soma=0; int qt=0;
        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);
            if(p.getStatus().equals("ENTREGUE") && p.getDistanciaKm()>=0){
                soma+=p.getDistanciaKm(); qt++;
            }
        }
        if(qt==0){
            System.out.println("\n--- Distância/Tempo/Frete ---\nSem dados.");
            return;
        }
        double km=soma/qt, tempo=5.0*km, frete=3 + (2.50*km);
        System.out.println("\n--- Distância/Tempo/Frete ---");
        System.out.println("Distância média: "+Helpers.arred2(km)+" km");
        System.out.println("Tempo médio (est.): "+Helpers.arred2(tempo)+" min");
        System.out.println("Frete médio (est.): R$ "+Helpers.arred2(frete));
    }
    public static double satisfacao(ArrayList<Pedido> pedidos){
        int soma=0,qt=0;
        for(int i=0;i<pedidos.size();i++){
            Pedido p=pedidos.get(i);
            if(p.getStatus().equals("ENTREGUE") && p.getAvaliacao()>0){
                soma+=p.getAvaliacao(); qt++;
            }
        }
        if(qt==0){
            return 0.0;
        }
        double media = (double)soma/(double)qt;
        if(media<1){
            media=1;
        }
        if(media>5){
            media = 5;
        }
    return media;
    }
}