package javaDeliveryPizza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

public class Relatorios {

    private static double ticketMedioIntegrado(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        double soma = 0.0;
        int contagem = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus())){
                soma += Helpers.valorTotal(pedido);
                contagem++;
            }
        }

        for(int i=0;i<eventos.size();i++){
            Evento evento = eventos.get(i);
            if("REALIZADO".equals(evento.getStatus())){
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

    private static void produtoMaisConsumidoPorDia(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 2) Qual produto do restaurante (salão + eventos) mais consumido em cada dia da semana?");
        for(int dia=1; dia<=7; dia++){
            Map<String, Integer> contagem = new HashMap<String, Integer>();

            for(int i=0;i<pedidos.size();i++){
                Pedido pedido = pedidos.get(i);
                if("SERVIDO".equals(pedido.getStatus()) && pedido.getDiaSemana() == dia){
                    for(int j=0;j<pedido.getItens().size();j++){
                        ItemProduto item = pedido.getItens().get(j);
                        String nome = item.getProduto().descricao();
                        contagem.put(nome, contagem.getOrDefault(nome, 0) + item.getQuantidade());
                    }
                }
            }

            for(int i=0;i<eventos.size();i++){
                Evento evento = eventos.get(i);
                if(evento.getDiaSemana() == dia && !"CANCELADO".equals(evento.getStatus())){
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

    private static void cancelamentosEmDiasComEvento(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 4) Em dias com eventos, quantas vendas do salão foram canceladas e quais motivos apareceram?");
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
            if("CANCELADO".equals(pedido.getStatus()) && diasComEvento.contains(pedido.getDiaSemana())){
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
                System.out.println(Helpers.nomeDia(dia) + ": eventos previstos, sem cancelamentos de vendas.");
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
            System.out.println("Não houve cancelamentos de vendas do restaurante nos dias com eventos.");
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
                soma += Helpers.valorTotal(pedido);
                contagem++;
            }
        }
        if(contagem == 0){
            System.out.println("O salão não registrou vendas nos dias com eventos.");
            return;
        }
        System.out.println("Total de vendas analisadas: " + contagem);
        System.out.println("Faturamento acumulado no salão: R$ " + Helpers.arred2(soma));
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

    private static void ocupacaoEVolume(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 8) Como está a ocupação dos eventos e o volume de itens do restaurante por dia da semana?");
        for(int dia=1; dia<=7; dia++){
            int capacidade = 0;
            int publico = 0;
            int qtEventos = 0;
            int itensBuffet = 0;

            for(int i=0;i<eventos.size();i++){
                Evento evento = eventos.get(i);
                if(evento.getDiaSemana() == dia && !"CANCELADO".equals(evento.getStatus())){
                    qtEventos++;
                    capacidade += evento.getCapacidade();
                    if("REALIZADO".equals(evento.getStatus())){
                        publico += evento.getPublicoReal();
                    }
                    for(int j=0;j<evento.getBuffet().size();j++){
                        itensBuffet += evento.getBuffet().get(j).getQuantidade();
                    }
                }
            }

            int itensSalao = 0;
            for(int i=0;i<pedidos.size();i++){
                Pedido pedido = pedidos.get(i);
                if("SERVIDO".equals(pedido.getStatus()) && pedido.getDiaSemana() == dia){
                    for(int j=0;j<pedido.getItens().size();j++){
                        itensSalao += pedido.getItens().get(j).getQuantidade();
                    }
                }
            }

            int totalItens = itensBuffet + itensSalao;

            if(qtEventos == 0 && totalItens == 0){
                System.out.println(Helpers.nomeDia(dia) + ": —");
                continue;
            }

            double ocupacao = 0.0;
            if(qtEventos > 0 && capacidade > 0){
                ocupacao = ((double)publico / (double)capacidade) * 100.0;
            }

            System.out.println(Helpers.nomeDia(dia) + ": " + qtEventos + " evento(s) | Ocupação " + Helpers.arred2(ocupacao) + "% | Itens no salão: " + itensSalao + " | Itens nos eventos: " + itensBuffet + " | Total de itens: " + totalItens);
        }
    }

    private static void faturamentoTotal(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nPergunta 9) Quanto o restaurante faturou ao combinar salão e eventos?");
        double receitaSalao = 0.0;
        int vendasServidas = 0;

        for(int i=0;i<pedidos.size();i++){
            Pedido pedido = pedidos.get(i);
            if("SERVIDO".equals(pedido.getStatus())){
                receitaSalao += Helpers.valorTotal(pedido);
                vendasServidas++;
            }
        }

        double receitaEventos = 0.0;
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            receitaEventos += ev.receitaIngressos() + Helpers.totalBuffet(ev);
        }

        double receitaTotal = receitaSalao + receitaEventos;
        System.out.println("Receita do salão: R$ " + Helpers.arred2(receitaSalao));
        System.out.println("Vendas servidas: " + vendasServidas);
        System.out.println("Receita dos eventos (ingressos + buffet): R$ " + Helpers.arred2(receitaEventos));
        System.out.println("Faturamento consolidado: R$ " + Helpers.arred2(receitaTotal));
    }

    public static void exibirRelatorioCompleto(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\n=== RELATÓRIO COMPLETO ===");
        System.out.println("Pergunta 1) Qual o ticket médio combinado entre vendas do salão e buffets de eventos realizados?");
        System.out.println("Resposta: R$ " + Helpers.arred2(ticketMedioIntegrado(pedidos, eventos)));

        produtoMaisConsumidoPorDia(pedidos, eventos);
        topSabores(pedidos, eventos);
        cancelamentosEmDiasComEvento(pedidos, eventos);
        faturamentoRestauranteEmDiasDeEvento(pedidos, eventos);

        System.out.println("\nPergunta 6) Qual a satisfação média combinada entre clientes do salão e participantes dos eventos?");
        System.out.println("Resposta: " + Helpers.arred2(satisfacaoIntegrada(pedidos, eventos)) + " / 5");

        eventoMaiorBuffet(eventos);
        ocupacaoEVolume(pedidos, eventos);
        faturamentoTotal(pedidos, eventos);
    }
}
