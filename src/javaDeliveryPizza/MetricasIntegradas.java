package javaDeliveryPizza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MetricasIntegradas {

    public static double ticketMedioIntegrado(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        double soma = 0.0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if(pedido.getStatus().equals("ENTREGUE")){
                soma += Helpers.valorTotal(pedido);
                contagem++;
            }
        }

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if(evento.getStatus().equals("REALIZADO")){
                double valorBuffet = Helpers.totalBuffet(evento);
                if(valorBuffet > 0){
                    soma += valorBuffet;
                    contagem++;
                }
            }
        }

        if(contagem == 0){
            return 0.0;
        }
        return soma / contagem;
    }

    public static void produtoMaisVendidoPorDiaIntegrado(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n--- Produtos mais consumidos por dia (delivery + eventos) ---");
        for(int dia=1; dia<=7; dia++){
            Map<String, Integer> contagem = new HashMap<String, Integer>();

            for(int i=0;i<pedidos.size();i++){
                Pedido pedido = pedidos.get(i);
                if(pedido.getStatus().equals("ENTREGUE") && pedido.getDiaSemana() == dia){
                    for(int j=0;j<pedido.getItens().size();j++){
                        ItemProduto item = pedido.getItens().get(j);
                        String nome = item.getProduto().descricao();
                        contagem.put(nome, contagem.getOrDefault(nome, 0) + item.getQuantidade());
                    }
                }
            }

            for(int i=0;i<eventos.size();i++){
                Evento evento = eventos.get(i);
                if(evento.getDiaSemana() == dia && !evento.getStatus().equals("CANCELADO")){
                    for(int j=0;j<evento.getBuffet().size();j++){
                        ItemProduto item = evento.getBuffet().get(j);
                        String nome = item.getProduto().descricao();
                        contagem.put(nome, contagem.getOrDefault(nome, 0) + item.getQuantidade());
                    }
                }
            }

            if(contagem.size() == 0){
                System.out.println(Helpers.nomeDia(dia) + ": —");
            }else{
                String escolhido = null;
                int maior = -1;
                for(Map.Entry<String, Integer> entry : contagem.entrySet()){
                    if(entry.getValue() > maior){
                        maior = entry.getValue();
                        escolhido = entry.getKey();
                    }
                }
                System.out.println(Helpers.nomeDia(dia) + ": " + escolhido + " (" + maior + ")");
            }
        }
    }

    public static void topSaboresIntegrado(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n--- Top 3 sabores de pizza (delivery + eventos) ---");
        Map<String, Integer> contagem = new HashMap<String, Integer>();

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if(pedido.getStatus().equals("ENTREGUE")){
                for(int j=0;j<pedido.getItens().size();j++){
                    ItemProduto item = pedido.getItens().get(j);
                    if(item.getProduto() instanceof Pizza){
                        Pizza pizza = (Pizza)item.getProduto();
                        String sabor = pizza.getSabor();
                        contagem.put(sabor, contagem.getOrDefault(sabor, 0) + item.getQuantidade());
                    }
                }
            }
        }

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if(!evento.getStatus().equals("CANCELADO")){
                for(int j=0;j<evento.getBuffet().size();j++){
                    ItemProduto item = evento.getBuffet().get(j);
                    if(item.getProduto() instanceof Pizza){
                        Pizza pizza = (Pizza)item.getProduto();
                        String sabor = pizza.getSabor();
                        contagem.put(sabor, contagem.getOrDefault(sabor, 0) + item.getQuantidade());
                    }
                }
            }
        }

        for(int pos=1; pos<=3; pos++){
            if(contagem.size() == 0){
                if(pos == 1){
                    System.out.println("(sem dados)");
                }
                break;
            }
            String melhor = null;
            int maior = -1;
            for(Map.Entry<String, Integer> entry : contagem.entrySet()){
                if(entry.getValue() > maior){
                    maior = entry.getValue();
                    melhor = entry.getKey();
                }
            }
            System.out.println(pos + ") " + melhor + " — " + maior);
            contagem.remove(melhor);
        }
    }

    public static void cancelamentosEmDiasComEvento(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n--- Cancelamentos de pedidos em dias com eventos ---");
        Set<Integer> diasComEvento = new HashSet<Integer>();
        for(int i=0;i<eventos.size();i++){
            diasComEvento.add(eventos.get(i).getDiaSemana());
        }
        if(diasComEvento.size() == 0){
            System.out.println("Não há eventos cadastrados.");
            return;
        }

        Map<Integer, Integer> totalPorDia = new HashMap<Integer, Integer>();
        Map<Integer, Map<String, Integer>> motivosPorDia = new HashMap<Integer, Map<String,Integer>>();

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if(pedido.getStatus().equals("CANCELADO") && diasComEvento.contains(pedido.getDiaSemana())){
                int dia = pedido.getDiaSemana();
                totalPorDia.put(dia, totalPorDia.getOrDefault(dia, 0) + 1);

                String motivo = pedido.getMotivoCancelamento();
                if(motivo == null || motivo.trim().length() == 0){
                    motivo = "Não informado";
                }
                Map<String, Integer> motivos = motivosPorDia.get(dia);
                if(motivos == null){
                    motivos = new HashMap<String, Integer>();
                    motivosPorDia.put(dia, motivos);
                }
                motivos.put(motivo, motivos.getOrDefault(motivo, 0) + 1);
            }
        }

        boolean houveCancelamento = false;
        for(int dia=1; dia<=7; dia++){
            if(!diasComEvento.contains(dia)){
                continue;
            }
            int total = totalPorDia.getOrDefault(dia, 0);
            if(total == 0){
                System.out.println(Helpers.nomeDia(dia) + ": eventos previstos, sem cancelamentos de pedidos.");
            }else{
                houveCancelamento = true;
                System.out.println(Helpers.nomeDia(dia) + ": " + total + " cancelamento(s)");
                Map<String, Integer> motivos = motivosPorDia.get(dia);
                if(motivos != null){
                    int pos = 1;
                    while(motivos.size() > 0){
                        String escolhido = null;
                        int maior = -1;
                        for(Map.Entry<String, Integer> entry : motivos.entrySet()){
                            if(entry.getValue() > maior){
                                maior = entry.getValue();
                                escolhido = entry.getKey();
                            }
                        }
                        System.out.println("   " + pos + ") " + escolhido + " — " + maior);
                        motivos.remove(escolhido);
                        pos++;
                    }
                }
            }
        }

        if(!houveCancelamento){
            System.out.println("Não houve cancelamentos de pedidos nos dias com eventos.");
        }
    }

    public static void distanciaEmDiasDeEvento(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n--- Distância média das entregas em dias com eventos ---");
        Set<Integer> diasComEvento = new HashSet<Integer>();
        int totalEventos = 0;
        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            int dia = evento.getDiaSemana();
            diasComEvento.add(dia);
            totalEventos++;
        }

        if(diasComEvento.size() == 0){
            System.out.println("Não há eventos cadastrados.");
            return;
        }

        double soma = 0.0;
        int contagem = 0;
        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if(pedido.getStatus().equals("ENTREGUE") && pedido.getDistanciaKm() >= 0 && diasComEvento.contains(pedido.getDiaSemana())){
                soma += pedido.getDistanciaKm();
                contagem++;
            }
        }

        if(contagem == 0){
            System.out.println("Nenhuma entrega realizada nos dias em que há eventos.");
            return;
        }

        double mediaKm = soma / contagem;
        double tempo = 5.0 * mediaKm;
        double frete = 3 + (2.50 * mediaKm);
        System.out.println("Entregas analisadas: " + contagem);
        System.out.println("Eventos associados: " + totalEventos);
        System.out.println("Distância média: " + Helpers.arred2(mediaKm) + " km");
        System.out.println("Tempo médio (est.): " + Helpers.arred2(tempo) + " min");
        System.out.println("Frete médio (est.): R$ " + Helpers.arred2(frete));
    }

    public static double satisfacaoIntegrada(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        int soma = 0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if(pedido.getStatus().equals("ENTREGUE") && pedido.getAvaliacao() > 0){
                soma += pedido.getAvaliacao();
                contagem++;
            }
        }

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if(evento.getStatus().equals("REALIZADO") && evento.getAvaliacao() > 0){
                soma += evento.getAvaliacao();
                contagem++;
            }
        }

        if(contagem == 0){
            return 0.0;
        }
        double media = (double)soma / (double)contagem;
        if(media < 1){
            media = 1;
        }
        if(media > 5){
            media = 5;
        }
        return media;
    }

    public static void ocupacaoEVolume(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n--- Ocupação de eventos e volume de itens por dia ---");
        for(int dia=1; dia<=7; dia++){
            int capacidade = 0;
            int publico = 0;
            int qtEventos = 0;
            int itensBuffet = 0;

            for(int i=0;i<eventos.size();i++){
                Evento evento = eventos.get(i);
                if(evento.getDiaSemana() == dia && !evento.getStatus().equals("CANCELADO")){
                    qtEventos++;
                    capacidade += evento.getCapacidade();
                    if(evento.getStatus().equals("REALIZADO")){
                        publico += evento.getPublicoReal();
                    }
                    for(int j=0;j<evento.getBuffet().size();j++){
                        itensBuffet += evento.getBuffet().get(j).getQuantidade();
                    }
                }
            }

            int itensDelivery = 0;
            for(int i=0;i<pedidos.size();i++){
                Pedido pedido = pedidos.get(i);
                if(pedido.getStatus().equals("ENTREGUE") && pedido.getDiaSemana() == dia){
                    for(int j=0;j<pedido.getItens().size();j++){
                        itensDelivery += pedido.getItens().get(j).getQuantidade();
                    }
                }
            }

            int totalItens = itensBuffet + itensDelivery;

            if(qtEventos == 0 && totalItens == 0){
                System.out.println(Helpers.nomeDia(dia) + ": —");
                continue;
            }

            double ocupacao = 0.0;
            if(qtEventos > 0 && capacidade > 0){
                ocupacao = ((double)publico / (double)capacidade) * 100.0;
            }

            System.out.println(Helpers.nomeDia(dia) + ": " + qtEventos + " evento(s) | Ocupação " + Helpers.arred2(ocupacao) + "% | Itens do delivery: " + itensDelivery + " | Itens nos eventos: " + itensBuffet + " | Total de itens: " + totalItens);
        }
    }
}
