package javaDeliveryPizza;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class PersistenciaJson {

    private static String escape(String texto){
        if(texto == null){
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    private static String formatDouble(double valor){
        return String.format(Locale.US, "%.2f", valor);
    }

    private static String itensJson(ArrayList<ItemProduto> itens){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<itens.size();i++){
            if(i>0){
                sb.append(",");
            }
            ItemProduto item = itens.get(i);
            Produto produto = item.getProduto();
            sb.append("{\"descricao\":\"")
              .append(escape(produto.descricao()))
              .append("\",\"quantidade\":")
              .append(item.getQuantidade())
              .append(",\"precoUnitario\":")
              .append(formatDouble(produto.getPreco()))
              .append("}");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void salvarDados(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"vendasRestaurante\":[");
        for(int i=0;i<pedidos.size();i++){
            if(i>0){
                sb.append(",");
            }
            Pedido pedido = pedidos.get(i);
            sb.append("{\"id\":")
              .append(pedido.getNumPedido())
              .append(",\"diaSemana\":")
              .append(pedido.getDiaSemana())
              .append(",\"status\":\"")
              .append(escape(pedido.getStatus()))
              .append("\",\"avaliacao\":")
              .append(pedido.getAvaliacao())
              .append(",\"motivoCancelamento\":\"")
              .append(escape(pedido.getMotivoCancelamento()))
              .append("\",\"valorTotal\":")
              .append(formatDouble(Helpers.valorTotal(pedido)))
              .append(",\"itens\":")
              .append(itensJson(pedido.getItens()))
              .append("}");
        }
        sb.append("],\"eventos\":[");
        for(int i=0;i<eventos.size();i++){
            if(i>0){
                sb.append(",");
            }
            Evento evento = eventos.get(i);
            sb.append("{\"id\":")
              .append(evento.getId())
              .append(",\"nome\":\"")
              .append(escape(evento.getNome()))
              .append("\",\"tipo\":\"")
              .append(escape(evento.getTipo()))
              .append("\",\"diaSemana\":")
              .append(evento.getDiaSemana())
              .append(",\"status\":\"")
              .append(escape(evento.getStatus()))
              .append("\",\"capacidade\":")
              .append(evento.getCapacidade())
              .append(",\"ingressosVendidos\":")
              .append(evento.getIngressosVendidos())
              .append(",\"publicoReal\":")
              .append(evento.getPublicoReal())
              .append(",\"avaliacao\":")
              .append(evento.getAvaliacao())
              .append(",\"precoIngresso\":")
              .append(formatDouble(evento.getPrecoIngresso()))
              .append(",\"receitaBuffet\":")
              .append(formatDouble(Helpers.totalBuffet(evento)))
              .append(",\"itensBuffet\":")
              .append(itensJson(evento.getBuffet()))
              .append("}");
        }
        sb.append("]}");

        try(FileWriter writer = new FileWriter("dados.json")){
            writer.write(sb.toString());
        }catch(IOException e){
            System.out.println("Falha ao salvar dados em JSON.");
        }
    }
}
