package javaDeliveryPizza.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javaDeliveryPizza.domain.Evento;
import javaDeliveryPizza.domain.ItemProduto;
import javaDeliveryPizza.domain.Pedido;
import javaDeliveryPizza.util.AnalyticsHelper;
import javaDeliveryPizza.domain.Pizza;

public class Relatorios {

    private static double ticketMedioSalao(ArrayList<Pedido> pedidos){
        double soma = 0.0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus())){
                soma += AnalyticsHelper.valorTotal(pedido);
                contagem++;
            }
        }

        if(contagem == 0){
            return 0.0;
        }
        return soma / contagem;
    }

    private static double ticketMedioEventos(ArrayList<Evento> eventos){
        double soma = 0.0;
        int contagem = 0;

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if("REALIZADO".equals(evento.getStatus())){
                double valorBuffet = AnalyticsHelper.totalBuffet(evento);
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

    private static double ticketMedioIntegrado(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        double soma = 0.0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus())){
                soma += AnalyticsHelper.valorTotal(pedido);
                contagem++;
            }
        }

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if("REALIZADO".equals(evento.getStatus())){
                double valorBuffet = AnalyticsHelper.totalBuffet(evento);
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

    private static Map.Entry<String, Integer> maiorEntrada(Map<String, Integer> mapa){
        Map.Entry<String, Integer> melhor = null;
        for(Map.Entry<String, Integer> entry : mapa.entrySet()){
            if(melhor == null || entry.getValue() > melhor.getValue()){
                melhor = entry;
            }
        }
        return melhor;
    }

    private static void produtoMaisConsumidoPorDia(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 2) Qual produto do restaurante foi mais consumido no salão, nos eventos e no combinado em cada dia da semana?");
        for(int dia=1; dia<=7; dia++){
            Map<String, Integer> consumoSalao = new HashMap<String, Integer>();
            Map<String, Integer> consumoEventos = new HashMap<String, Integer>();

            for(int i=0;i<pedidos.size();i++){
                Pedido pedido = pedidos.get(i);
                if("SERVIDO".equals(pedido.getStatus()) && pedido.getDiaSemana() == dia){
                    for(int j=0;j<pedido.getItens().size();j++){
                        ItemProduto item = pedido.getItens().get(j);
                        String nome = item.getProduto().descricao();
                        consumoSalao.put(nome, consumoSalao.getOrDefault(nome, 0) + item.getQuantidade());
                    }
                }
            }

            for(int i=0;i<eventos.size();i++){
                Evento evento = eventos.get(i);
                if(evento.getDiaSemana() == dia && !"CANCELADO".equals(evento.getStatus())){
                    for(int j=0;j<evento.getBuffet().size();j++){
                        ItemProduto item = evento.getBuffet().get(j);
                        String nome = item.getProduto().descricao();
                        consumoEventos.put(nome, consumoEventos.getOrDefault(nome, 0) + item.getQuantidade());
                    }
                }
            }

            Map<String, Integer> consumoCombinado = new HashMap<String, Integer>();
            consumoCombinado.putAll(consumoSalao);
            for(Map.Entry<String, Integer> entry : consumoEventos.entrySet()){
                consumoCombinado.put(entry.getKey(), consumoCombinado.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }

            if(consumoSalao.size() == 0 && consumoEventos.size() == 0){
                System.out.println(AnalyticsHelper.nomeDia(dia) + ": —");
                continue;
            }

            System.out.println(AnalyticsHelper.nomeDia(dia) + ":");
            Map.Entry<String, Integer> melhorSalao = maiorEntrada(consumoSalao);
            Map.Entry<String, Integer> melhorEventos = maiorEntrada(consumoEventos);
            Map.Entry<String, Integer> melhorCombinado = maiorEntrada(consumoCombinado);

            if(melhorSalao == null){
                System.out.println("   Salão: —");
            }else{
                System.out.println("   Salão: " + melhorSalao.getKey() + " (" + melhorSalao.getValue() + ")");
            }

            if(melhorEventos == null){
                System.out.println("   Eventos: —");
            }else{
                System.out.println("   Eventos: " + melhorEventos.getKey() + " (" + melhorEventos.getValue() + ")");
            }

            if(melhorCombinado == null){
                System.out.println("   Combinado: —");
            }else{
                System.out.println("   Combinado: " + melhorCombinado.getKey() + " (" + melhorCombinado.getValue() + ")");
            }
        }
    }

    private static void topSabores(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 3) Quais os três sabores de pizza mais servidos somando salão e eventos?");
        Map<String, Integer> contagem = new HashMap<String, Integer>();

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus())){
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
            if(!"CANCELADO".equals(evento.getStatus())){
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

    private static void adesaoBuffetEventos(ArrayList<Evento> eventos){
        System.out.println("\nPergunta 4) Entre os eventos realizados, quantos contrataram buffet do restaurante e qual receita média gerada?");
        int totalRealizados = 0;
        int comBuffet = 0;
        double somaBuffet = 0.0;
        int totalItens = 0;

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if("REALIZADO".equals(evento.getStatus())){
                totalRealizados++;
                double valorBuffet = AnalyticsHelper.totalBuffet(evento);
                if(valorBuffet > 0){
                    comBuffet++;
                    somaBuffet += valorBuffet;
                    for(int j=0;j<evento.getBuffet().size();j++){
                        totalItens += evento.getBuffet().get(j).getQuantidade();
                    }
                }
            }
        }

        if(totalRealizados == 0){
            System.out.println("Não há eventos realizados para análise.");
            return;
        }

        double percentual = ((double)comBuffet / (double)totalRealizados) * 100.0;
        System.out.println("Eventos realizados: " + totalRealizados);
        System.out.println("Eventos com buffet do restaurante: " + comBuffet + " (" + AnalyticsHelper.arred2(percentual) + "%)");
        if(comBuffet > 0){
            double mediaBuffet = somaBuffet / (double)comBuffet;
            double mediaItens = (double)totalItens / (double)comBuffet;
            System.out.println("Receita média por buffet contratado: R$ " + AnalyticsHelper.arred2(mediaBuffet));
            System.out.println("Itens médios fornecidos pelo restaurante: " + AnalyticsHelper.arred2(mediaItens));
        }else{
            System.out.println("Nenhum evento realizado contratou o buffet do restaurante.");
        }
    }

    private static void faturamentoRestauranteEmDiasDeEvento(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 5) Qual foi o faturamento do salão nos dias em que houveram eventos?");
        Set<Integer> diasComEvento = new HashSet<Integer>();
        for(int i=0;i<eventos.size();i++){
            diasComEvento.add(eventos.get(i).getDiaSemana());
        }
        if(diasComEvento.size() == 0){
            System.out.println("Não há eventos cadastrados.");
            return;
        }
        double soma = 0.0;
        int contagem = 0;
        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus()) && diasComEvento.contains(pedido.getDiaSemana())){
                soma += AnalyticsHelper.valorTotal(pedido);
                contagem++;
            }
        }
        if(contagem == 0){
            System.out.println("O salão não registrou vendas nos dias com eventos.");
            return;
        }
        System.out.println("Total de vendas analisadas: " + contagem);
        System.out.println("Faturamento acumulado no salão: R$ " + AnalyticsHelper.arred2(soma));
    }

    private static double satisfacaoSalao(ArrayList<Pedido> pedidos){
        int soma = 0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus()) && pedido.getAvaliacao() > 0){
                soma += pedido.getAvaliacao();
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

    private static double satisfacaoEventos(ArrayList<Evento> eventos){
        int soma = 0;
        int contagem = 0;

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if("REALIZADO".equals(evento.getStatus()) && evento.getAvaliacao() > 0){
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

    private static double satisfacaoIntegrada(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        int soma = 0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus()) && pedido.getAvaliacao() > 0){
                soma += pedido.getAvaliacao();
                contagem++;
            }
        }

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if("REALIZADO".equals(evento.getStatus()) && evento.getAvaliacao() > 0){
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

    private static void eventoMaiorBuffet(ArrayList<Evento> eventos){
        System.out.println("\nPergunta 7) Qual evento gerou a maior receita de buffet do restaurante?");
        if(eventos.size() == 0){
            System.out.println("(nenhum evento)");
            return;
        }

        double maiorValor = -1.0;
        Evento destaque = null;
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            double valor = AnalyticsHelper.totalBuffet(ev);
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
        System.out.println("Tipo: " + destaque.getTipo() + " | Dia: " + AnalyticsHelper.nomeDia(destaque.getDiaSemana()));
        System.out.println("Valor total do buffet: R$ " + AnalyticsHelper.arred2(maiorValor));
    }

    private static void receitaCombinadaPorDia(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 8) Qual é a receita combinada por dia da semana e como ela se divide entre salão e eventos?");
        for(int dia=1; dia<=7; dia++){
            double receitaSalao = 0.0;
            for(int i=0;i<pedidos.size();i++){
                Pedido pedido = pedidos.get(i);
                if("SERVIDO".equals(pedido.getStatus()) && pedido.getDiaSemana() == dia){
                    receitaSalao += AnalyticsHelper.valorTotal(pedido);
                }
            }

            double receitaEventos = 0.0;
            int eventosRealizados = 0;
            for(int i=0;i<eventos.size();i++){
                Evento evento = eventos.get(i);
                if(evento.getDiaSemana() == dia && "REALIZADO".equals(evento.getStatus())){
                    eventosRealizados++;
                    receitaEventos += evento.receitaIngressos() + AnalyticsHelper.totalBuffet(evento);
                }
            }

            double total = receitaSalao + receitaEventos;
            if(total == 0){
                System.out.println(AnalyticsHelper.nomeDia(dia) + ": —");
                continue;
            }

            double percSalao = (receitaSalao / total) * 100.0;
            double percEventos = (receitaEventos / total) * 100.0;

            System.out.println(AnalyticsHelper.nomeDia(dia) + ": R$ " + AnalyticsHelper.arred2(total) + " | Salão: R$ " + AnalyticsHelper.arred2(receitaSalao) + " (" + AnalyticsHelper.arred2(percSalao) + "%) | Eventos: R$ " + AnalyticsHelper.arred2(receitaEventos) + " (" + AnalyticsHelper.arred2(percEventos) + "%) | Eventos realizados: " + eventosRealizados);
        }
    }

    private static void faturamentoTotal(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 9) Quanto o restaurante faturou ao combinar salão e eventos?");
        double receitaSalao = 0.0;
        int vendasServidas = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus())){
                receitaSalao += AnalyticsHelper.valorTotal(pedido);
                vendasServidas++;
            }
        }

        double receitaEventos = 0.0;
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            receitaEventos += ev.receitaIngressos() + AnalyticsHelper.totalBuffet(ev);
        }

        double receitaTotal = receitaSalao + receitaEventos;
        System.out.println("Receita do salão: R$ " + AnalyticsHelper.arred2(receitaSalao));
        System.out.println("Vendas servidas: " + vendasServidas);
        System.out.println("Receita dos eventos (ingressos + buffet): R$ " + AnalyticsHelper.arred2(receitaEventos));
        System.out.println("Faturamento consolidado: R$ " + AnalyticsHelper.arred2(receitaTotal));
    }

    public static void exibirRelatorioCompleto(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n=== RELATÓRIO COMPLETO ===");
        System.out.println("Pergunta 1) Qual o ticket médio somente do salão, somente dos buffets de eventos realizados e o combinado dos dois?");
        System.out.println("   Salão: R$ " + AnalyticsHelper.arred2(ticketMedioSalao(pedidos)));
        System.out.println("   Eventos: R$ " + AnalyticsHelper.arred2(ticketMedioEventos(eventos)));
        System.out.println("   Combinado: R$ " + AnalyticsHelper.arred2(ticketMedioIntegrado(pedidos, eventos)));

        produtoMaisConsumidoPorDia(pedidos, eventos);
        topSabores(pedidos, eventos);
        adesaoBuffetEventos(eventos);
        faturamentoRestauranteEmDiasDeEvento(pedidos, eventos);

        System.out.println("\nPergunta 6) Qual a satisfação média do salão, dos eventos e a visão combinada?");
        System.out.println("   Salão: " + AnalyticsHelper.arred2(satisfacaoSalao(pedidos)) + " / 5");
        System.out.println("   Eventos: " + AnalyticsHelper.arred2(satisfacaoEventos(eventos)) + " / 5");
        System.out.println("   Combinado: " + AnalyticsHelper.arred2(satisfacaoIntegrada(pedidos, eventos)) + " / 5");

        eventoMaiorBuffet(eventos);
        receitaCombinadaPorDia(pedidos, eventos);
        faturamentoTotal(pedidos, eventos);
    }
}
