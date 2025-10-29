package javaDeliveryPizza.src;

import java.util.ArrayList;

public class MetricasEventos {

    public static void resumo(ArrayList<Evento> eventos){
        System.out.println("\n--- Resumo de eventos ---");
        if(eventos.size() == 0){
            System.out.println("(nenhum evento)");
            return;
        }

        int agendados = 0;
        int realizados = 0;
        int cancelados = 0;
        double receitaIngressos = 0.0;
        double receitaBuffet = 0.0;
        int publicoTotal = 0;
        int eventosComPublico = 0;
        int somaAvaliacoes = 0;
        int qtdAvaliacoes = 0;

        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            if(ev.getStatus().equals("AGENDADO")){
                agendados++;
            } else if(ev.getStatus().equals("REALIZADO")){
                realizados++;
                publicoTotal += ev.getPublicoReal();
                eventosComPublico++;
                if(ev.getAvaliacao() > 0){
                    somaAvaliacoes += ev.getAvaliacao();
                    qtdAvaliacoes++;
                }
            } else if(ev.getStatus().equals("CANCELADO")){
                cancelados++;
            }

            receitaIngressos += ev.receitaIngressos();
            receitaBuffet += Helpers.totalBuffet(ev);
        }

        System.out.println("Agendados: " + agendados);
        System.out.println("Realizados: " + realizados);
        System.out.println("Cancelados: " + cancelados);
        System.out.println("Receita com ingressos: R$ " + Helpers.arred2(receitaIngressos));
        System.out.println("Receita com buffet: R$ " + Helpers.arred2(receitaBuffet));

        if(eventosComPublico > 0){
            double mediaPublico = (double)publicoTotal / (double)eventosComPublico;
            System.out.println("Público médio em eventos realizados: " + Helpers.arred2(mediaPublico));
        } else {
            System.out.println("Público médio em eventos realizados: —");
        }

        if(qtdAvaliacoes > 0){
            double mediaAval = (double)somaAvaliacoes / (double)qtdAvaliacoes;
            System.out.println("Satisfação média dos eventos: " + Helpers.arred2(mediaAval));
        } else {
            System.out.println("Satisfação média dos eventos: —");
        }
    }

    public static void topBuffet(ArrayList<Evento> eventos){
        System.out.println("\n--- Eventos com maior buffet ---");
        if(eventos.size() == 0){
            System.out.println("(nenhum evento)");
            return;
        }

        double maiorValor = -1.0;
        Evento destaque = null;
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            double valor = Helpers.totalBuffet(ev);
            if(valor > maiorValor){
                maiorValor = valor;
                destaque = ev;
            }
        }

        if(destaque == null || maiorValor <= 0){
            System.out.println("Nenhum evento possui buffet do restaurante.");
            return;
        }

        System.out.println("Evento #" + destaque.getId() + " - " + destaque.getNome());
        System.out.println("Tipo: " + destaque.getTipo() + " | Dia: " + Helpers.nomeDia(destaque.getDiaSemana()));
        System.out.println("Valor total do buffet: R$ " + Helpers.arred2(maiorValor));
    }

    public static void ocupacaoPorDia(ArrayList<Evento> eventos){
        System.out.println("\n--- Ocupação de eventos por dia ---");
        for(int dia=1; dia<=7; dia++){
            int capacidade = 0;
            int publico = 0;
            int qtEventos = 0;
            for(int i=0;i<eventos.size();i++){
                Evento ev = eventos.get(i);
                if(ev.getDiaSemana() == dia){
                    capacidade += ev.getCapacidade();
                    if(ev.getStatus().equals("REALIZADO")){
                        publico += ev.getPublicoReal();
                    }
                    qtEventos++;
                }
            }

            if(qtEventos == 0){
                System.out.println(Helpers.nomeDia(dia) + ": —");
            } else {
                double taxa = 0.0;
                if(capacidade > 0){
                    taxa = ((double)publico / (double)capacidade) * 100.0;
                }
                System.out.println(Helpers.nomeDia(dia) + ": " + qtEventos + " evento(s) | Capacidade " + capacidade + " | Público " + publico + " | Ocupação " + Helpers.arred2(taxa) + "%");
            }
        }
    }

    public static void relatorioIntegrado(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n--- Integração Restaurante + Eventos ---");
        double receitaDelivery = 0.0;
        int pedidosEntregues = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido p = pedidos.get(i);
            if(p.getStatus().equals("ENTREGUE")){
                receitaDelivery += Helpers.valorTotal(p);
                pedidosEntregues++;
            }
        }

        double receitaEventos = 0.0;
        int eventosComBuffet = 0;
        int itensBuffet = 0;
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            double valorBuffet = Helpers.totalBuffet(ev);
            if(valorBuffet > 0){
                eventosComBuffet++;
                for(int j=0;j<ev.getBuffet().size();j++){
                    itensBuffet += ev.getBuffet().get(j).getQuantidade();
                }
            }
            receitaEventos += ev.receitaIngressos() + valorBuffet;
        }

        double receitaTotal = receitaDelivery + receitaEventos;
        System.out.println("Receita do delivery: R$ " + Helpers.arred2(receitaDelivery));
        System.out.println("Pedidos entregues: " + pedidosEntregues);
        System.out.println("Receita dos eventos (ingressos + buffet): R$ " + Helpers.arred2(receitaEventos));
        System.out.println("Eventos com buffet do restaurante: " + eventosComBuffet);
        System.out.println("Total de itens do restaurante servidos em eventos: " + itensBuffet);
        System.out.println("Faturamento consolidado delivery + eventos: R$ " + Helpers.arred2(receitaTotal));
    }
}
