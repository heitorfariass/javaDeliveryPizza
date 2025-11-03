package javaDeliveryPizza;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Persistencia {
    private static final String ARQUIVO = "dados.json";

    public static void carregar(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        Path caminho = Paths.get(ARQUIVO);
        if(!Files.exists(caminho)){
            Cadastro.configurarProximoId(1);
            CadastroEventos.configurarProximoId(1);
            return;
        }

        try{
            String conteudo = Files.readString(caminho, StandardCharsets.UTF_8);
            if(conteudo.trim().isEmpty()){
                Cadastro.configurarProximoId(1);
                CadastroEventos.configurarProximoId(1);
                return;
            }
            conteudo = limparComentarios(conteudo);
            if(conteudo.trim().isEmpty()){
                Cadastro.configurarProximoId(1);
                CadastroEventos.configurarProximoId(1);
                return;
            }
            DadosCarregados dados = interpretarJson(conteudo, coletarIdsPedidos(pedidos), coletarIdsEventos(eventos), proximoId(pedidos), proximoId(eventos));
            pedidos.clear();
            pedidos.addAll(dados.pedidos);
            eventos.clear();
            eventos.addAll(dados.eventos);
            Cadastro.configurarProximoId(dados.proximoPedido);
            CadastroEventos.configurarProximoId(dados.proximoEvento);
        } catch(Exception ex){
            System.out.println("Falha ao carregar dados salvos: " + ex.getMessage());
            Cadastro.configurarProximoId(proximoId(pedidos));
            CadastroEventos.configurarProximoId(proximoId(eventos));
        }
    }

    public static void salvar(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        Path caminho = Paths.get(ARQUIVO);
        try{
            String json = gerarJson(pedidos, eventos);
            Files.writeString(caminho, json, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }catch(IOException ex){
            System.out.println("Não foi possível salvar os dados: " + ex.getMessage());
        }
    }

    public static void imprimirModelo(){
        System.out.println("\n=== Modelo JSON para pedidos/eventos fictícios ===");
        System.out.println("Cole este formato em dados.json ou utilize a opção de importação.");
        System.out.println("Você pode pedir ao ChatGPT: 'gere 2 pedidos e 1 evento neste formato'.");
        System.out.println("Ao colar no arquivo, remova os comentários (linhas começando com //).");
        System.out.println("{\n  \"pedidos\": [\n    {\n      \"numPedido\": 1, // pode deixar 0 para gerar automaticamente\n      \"diaSemana\": 4,\n      \"status\": \"ENTREGUE\",\n      \"avaliacao\": 5,\n      \"distanciaKm\": 6.5,\n      \"motivoCancelamento\": \"\",\n      \"itens\": [\n        {\n          \"tipo\": \"pizza\",\n          \"sabor\": \"Frango com catupiry\",\n          \"tamanho\": \"G\",\n          \"preco\": 52.0,\n          \"quantidade\": 2\n        },\n        {\n          \"tipo\": \"bebida\",\n          \"nome\": \"Coca-Cola\",\n          \"tamanho\": \"2L\",\n          \"preco\": 12.0,\n          \"quantidade\": 1\n        }\n      ]\n    }\n  ],\n  \"eventos\": [\n    {\n      \"id\": 1, // pode deixar 0 para gerar automaticamente\n      \"nome\": \"Noite da Pizza\",\n      \"tipo\": \"Festa corporativa\",\n      \"diaSemana\": 3,\n      \"status\": \"AGENDADO\",\n      \"capacidade\": 80,\n      \"publicoReal\": 0,\n      \"ingressosVendidos\": 40,\n      \"precoIngresso\": 35.0,\n      \"avaliacao\": 0,\n      \"buffet\": [\n        {\n          \"tipo\": \"pizza\",\n          \"sabor\": \"Calabresa\",\n          \"tamanho\": \"G\",\n          \"preco\": 48.0,\n          \"quantidade\": 10\n        }\n      ]\n    }\n  ]\n}\n");
    }

    public static boolean importarViaEntrada(Scanner sc, ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        System.out.println("\nCole o JSON (digite FIM sozinho na linha para concluir):");
        sc.nextLine();
        StringBuilder sb = new StringBuilder();
        while(true){
            String linha = sc.nextLine();
            if(linha.trim().equalsIgnoreCase("FIM")){
                break;
            }
            sb.append(linha).append('\n');
        }
        String conteudo = sb.toString().trim();
        if(conteudo.isEmpty()){
            System.out.println("Nenhum dado informado.");
            return false;
        }
        conteudo = limparComentarios(conteudo);
        try{
            Set<Integer> idsPedidos = coletarIdsPedidos(pedidos);
            Set<Integer> idsEventos = coletarIdsEventos(eventos);
            DadosCarregados dados = interpretarJson(conteudo, idsPedidos, idsEventos, proximoId(pedidos), proximoId(eventos));
            pedidos.addAll(dados.pedidos);
            eventos.addAll(dados.eventos);
            Cadastro.configurarProximoId(dados.proximoPedido);
            CadastroEventos.configurarProximoId(dados.proximoEvento);
            salvar(pedidos, eventos);
            System.out.println("Importação concluída: " + dados.pedidos.size() + " pedido(s) e " + dados.eventos.size() + " evento(s) adicionados.");
            return true;
        }catch(Exception ex){
            System.out.println("Falha ao importar: " + ex.getMessage());
            return false;
        }
    }

    private static int proximoId(ArrayList<? extends Object> lista){
        int max = 0;
        if(lista != null){
            if(lista.size() > 0){
                if(lista.get(0) instanceof Pedido){
                    for(Object obj : lista){
                        Pedido p = (Pedido)obj;
                        if(p.getNumPedido() > max){
                            max = p.getNumPedido();
                        }
                    }
                }else if(lista.get(0) instanceof Evento){
                    for(Object obj : lista){
                        Evento ev = (Evento)obj;
                        if(ev.getId() > max){
                            max = ev.getId();
                        }
                    }
                }
            }
        }
        return max + 1;
    }

    private static Set<Integer> coletarIdsPedidos(ArrayList<Pedido> pedidos){
        Set<Integer> ids = new HashSet<Integer>();
        for(Pedido p : pedidos){
            ids.add(p.getNumPedido());
        }
        return ids;
    }

    private static Set<Integer> coletarIdsEventos(ArrayList<Evento> eventos){
        Set<Integer> ids = new HashSet<Integer>();
        for(Evento e : eventos){
            ids.add(e.getId());
        }
        return ids;
    }

    private static String gerarJson(ArrayList<Pedido> pedidos, ArrayList<Evento> eventos){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"pedidos\": [\n");
        for(int i=0;i<pedidos.size();i++){
            Pedido p = pedidos.get(i);
            sb.append("    {");
            sb.append("\"numPedido\": ").append(p.getNumPedido()).append(", ");
            sb.append("\"diaSemana\": ").append(p.getDiaSemana()).append(", ");
            sb.append("\"status\": \"").append(escapar(p.getStatus())).append("\", ");
            sb.append("\"avaliacao\": ").append(p.getAvaliacao()).append(", ");
            sb.append("\"distanciaKm\": ").append(formatoNumero(p.getDistanciaKm())).append(", ");
            sb.append("\"motivoCancelamento\": \"").append(escapar(p.getMotivoCancelamento())).append("\", ");
            sb.append("\"itens\": [");
            ArrayList<ItemProduto> itens = p.getItens();
            for(int j=0;j<itens.size();j++){
                ItemProduto item = itens.get(j);
                sb.append("{");
                Produto produto = item.getProduto();
                if(produto instanceof Pizza){
                    Pizza pi = (Pizza)produto;
                    sb.append("\"tipo\": \"pizza\", ");
                    sb.append("\"sabor\": \"").append(escapar(pi.getSabor())).append("\", ");
                    sb.append("\"tamanho\": \"").append(escapar(pi.getTamanho())).append("\", ");
                    sb.append("\"preco\": ").append(formatoNumero(produto.getPreco())).append(", ");
                }else if(produto instanceof Bebida){
                    Bebida b = (Bebida)produto;
                    sb.append("\"tipo\": \"bebida\", ");
                    sb.append("\"nome\": \"").append(escapar(b.getNome())).append("\", ");
                    sb.append("\"tamanho\": \"").append(escapar(b.getTamanho())).append("\", ");
                    sb.append("\"preco\": ").append(formatoNumero(produto.getPreco())).append(", ");
                }else{
                    sb.append("\"tipo\": \"produto\", ");
                    sb.append("\"tamanho\": \"").append(escapar(produto.getTamanho())).append("\", ");
                    sb.append("\"preco\": ").append(formatoNumero(produto.getPreco())).append(", ");
                }
                sb.append("\"quantidade\": ").append(item.getQuantidade());
                sb.append("}");
                if(j < itens.size() - 1){
                    sb.append(", ");
                }
            }
            sb.append("]");
            sb.append("}");
            if(i < pedidos.size() - 1){
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("  ],\n");
        sb.append("  \"eventos\": [\n");
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            sb.append("    {");
            sb.append("\"id\": ").append(ev.getId()).append(", ");
            sb.append("\"nome\": \"").append(escapar(ev.getNome())).append("\", ");
            sb.append("\"tipo\": \"").append(escapar(ev.getTipo())).append("\", ");
            sb.append("\"diaSemana\": ").append(ev.getDiaSemana()).append(", ");
            sb.append("\"status\": \"").append(escapar(ev.getStatus())).append("\", ");
            sb.append("\"capacidade\": ").append(ev.getCapacidade()).append(", ");
            sb.append("\"publicoReal\": ").append(ev.getPublicoReal()).append(", ");
            sb.append("\"ingressosVendidos\": ").append(ev.getIngressosVendidos()).append(", ");
            sb.append("\"precoIngresso\": ").append(formatoNumero(ev.getPrecoIngresso())).append(", ");
            sb.append("\"avaliacao\": ").append(ev.getAvaliacao()).append(", ");
            sb.append("\"buffet\": [");
            ArrayList<ItemProduto> buffet = ev.getBuffet();
            for(int j=0;j<buffet.size();j++){
                ItemProduto item = buffet.get(j);
                Produto produto = item.getProduto();
                sb.append("{");
                if(produto instanceof Pizza){
                    Pizza pi = (Pizza)produto;
                    sb.append("\"tipo\": \"pizza\", ");
                    sb.append("\"sabor\": \"").append(escapar(pi.getSabor())).append("\", ");
                    sb.append("\"tamanho\": \"").append(escapar(pi.getTamanho())).append("\", ");
                    sb.append("\"preco\": ").append(formatoNumero(produto.getPreco())).append(", ");
                }else if(produto instanceof Bebida){
                    Bebida b = (Bebida)produto;
                    sb.append("\"tipo\": \"bebida\", ");
                    sb.append("\"nome\": \"").append(escapar(b.getNome())).append("\", ");
                    sb.append("\"tamanho\": \"").append(escapar(b.getTamanho())).append("\", ");
                    sb.append("\"preco\": ").append(formatoNumero(produto.getPreco())).append(", ");
                }else{
                    sb.append("\"tipo\": \"produto\", ");
                    sb.append("\"tamanho\": \"").append(escapar(produto.getTamanho())).append("\", ");
                    sb.append("\"preco\": ").append(formatoNumero(produto.getPreco())).append(", ");
                }
                sb.append("\"quantidade\": ").append(item.getQuantidade());
                sb.append("}");
                if(j < buffet.size() - 1){
                    sb.append(", ");
                }
            }
            sb.append("]");
            sb.append("}");
            if(i < eventos.size() - 1){
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String limparComentarios(String texto){
        String[] linhas = texto.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<linhas.length;i++){
            String linha = linhas[i];
            String trim = linha.trim();
            if(trim.startsWith("//") || trim.startsWith("#")){
                continue;
            }
            if(trim.length() == 0){
                continue;
            }
            sb.append(linha).append('\n');
        }
        return sb.toString();
    }

    private static String escapar(String valor){
        if(valor == null){
            return "";
        }
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String formatoNumero(double valor){
        return String.format(Locale.US, "%.2f", valor);
    }

    private static DadosCarregados interpretarJson(String conteudo, Set<Integer> idsPedidosExistentes, Set<Integer> idsEventosExistentes, int proximoPedido, int proximoEvento) throws Exception {
        SimpleJsonParser parser = new SimpleJsonParser(conteudo);
        Object obj = parser.parseValue();
        if(!(obj instanceof Map)){
            throw new IllegalArgumentException("Estrutura JSON inválida.");
        }
        Map<?,?> raiz = (Map<?,?>)obj;
        DadosCarregados dados = new DadosCarregados();
        Set<Integer> pedidosUsados = new HashSet<Integer>(idsPedidosExistentes);
        Set<Integer> eventosUsados = new HashSet<Integer>(idsEventosExistentes);

        Object pedidosObj = raiz.get("pedidos");
        int proximoPedidoAtual = proximoPedido;
        if(pedidosObj instanceof List){
            List<?> listaPedidos = (List<?>)pedidosObj;
            for(Object itemObj : listaPedidos){
                if(!(itemObj instanceof Map)){
                    continue;
                }
                Map<?,?> map = (Map<?,?>)itemObj;
                int numero = asInt(map.get("numPedido"));
                if(numero <= 0 || pedidosUsados.contains(numero)){
                    numero = proximoPedidoAtual;
                    proximoPedidoAtual++;
                }else{
                    pedidosUsados.add(numero);
                    if(numero >= proximoPedidoAtual){
                        proximoPedidoAtual = numero + 1;
                    }
                }
                Pedido pedido = new Pedido(numero);
                pedido.setDiaDaSemana(asInt(map.get("diaSemana")));
                String status = asString(map.get("status"));
                if(status == null || status.length() == 0){
                    status = "ENTREGUE";
                }
                pedido.setStatus(status);
                pedido.setAvaliacao(asInt(map.get("avaliacao")));
                pedido.setDistanciaKm(asDouble(map.get("distanciaKm")));
                String motivo = asString(map.get("motivoCancelamento"));
                if(motivo == null){
                    motivo = "";
                }
                pedido.setMotivoCancelamento(motivo);

                Object itensObj = map.get("itens");
                if(itensObj instanceof List){
                    List<?> itens = (List<?>)itensObj;
                    for(Object it : itens){
                        if(!(it instanceof Map)){
                            continue;
                        }
                        Map<?,?> itemMap = (Map<?,?>)it;
                        String tipo = asString(itemMap.get("tipo"));
                        int quantidade = asInt(itemMap.get("quantidade"));
                        if(quantidade <= 0){
                            quantidade = 1;
                        }
                        Produto produto = null;
                        if(tipo != null && tipo.equalsIgnoreCase("pizza")){
                            String sabor = asString(itemMap.get("sabor"));
                            String tamanho = asString(itemMap.get("tamanho"));
                            double preco = asDouble(itemMap.get("preco"));
                            produto = new Pizza(sabor == null ? "Pizza" : sabor, tamanho == null ? "M" : tamanho, preco);
                        }else if(tipo != null && tipo.equalsIgnoreCase("bebida")){
                            String nome = asString(itemMap.get("nome"));
                            String tamanho = asString(itemMap.get("tamanho"));
                            double preco = asDouble(itemMap.get("preco"));
                            produto = new Bebida(nome == null ? "Bebida" : nome, tamanho == null ? "Lata" : tamanho, preco);
                        }
                        if(produto != null){
                            pedido.addItem(new ItemProduto(produto, quantidade));
                        }
                    }
                }
                dados.pedidos.add(pedido);
            }
        }

        Object eventosObj = raiz.get("eventos");
        int proximoEventoAtual = proximoEvento;
        if(eventosObj instanceof List){
            List<?> listaEventos = (List<?>)eventosObj;
            for(Object itemObj : listaEventos){
                if(!(itemObj instanceof Map)){
                    continue;
                }
                Map<?,?> map = (Map<?,?>)itemObj;
                int numero = asInt(map.get("id"));
                if(numero <= 0 || eventosUsados.contains(numero)){
                    numero = proximoEventoAtual;
                    proximoEventoAtual++;
                }else{
                    eventosUsados.add(numero);
                    if(numero >= proximoEventoAtual){
                        proximoEventoAtual = numero + 1;
                    }
                }
                Evento evento = new Evento(numero);
                evento.setNome(asString(map.get("nome")));
                evento.setTipo(asString(map.get("tipo")));
                evento.setDiaSemana(asInt(map.get("diaSemana")));
                String status = asString(map.get("status"));
                if(status == null || status.length() == 0){
                    status = "AGENDADO";
                }
                evento.setStatus(status);
                evento.setCapacidade(asInt(map.get("capacidade")));
                evento.setPublicoReal(asInt(map.get("publicoReal")));
                evento.setIngressosVendidos(asInt(map.get("ingressosVendidos")));
                evento.setPrecoIngresso(asDouble(map.get("precoIngresso")));
                evento.setAvaliacao(asInt(map.get("avaliacao")));

                Object buffetObj = map.get("buffet");
                if(buffetObj instanceof List){
                    List<?> itens = (List<?>)buffetObj;
                    for(Object it : itens){
                        if(!(it instanceof Map)){
                            continue;
                        }
                        Map<?,?> itemMap = (Map<?,?>)it;
                        String tipo = asString(itemMap.get("tipo"));
                        int quantidade = asInt(itemMap.get("quantidade"));
                        if(quantidade <= 0){
                            quantidade = 1;
                        }
                        Produto produto = null;
                        if(tipo != null && tipo.equalsIgnoreCase("pizza")){
                            String sabor = asString(itemMap.get("sabor"));
                            String tamanho = asString(itemMap.get("tamanho"));
                            double preco = asDouble(itemMap.get("preco"));
                            produto = new Pizza(sabor == null ? "Pizza" : sabor, tamanho == null ? "M" : tamanho, preco);
                        }else if(tipo != null && tipo.equalsIgnoreCase("bebida")){
                            String nome = asString(itemMap.get("nome"));
                            String tamanho = asString(itemMap.get("tamanho"));
                            double preco = asDouble(itemMap.get("preco"));
                            produto = new Bebida(nome == null ? "Bebida" : nome, tamanho == null ? "Lata" : tamanho, preco);
                        }
                        if(produto != null){
                            evento.adicionarItemBuffet(new ItemProduto(produto, quantidade));
                        }
                    }
                }
                dados.eventos.add(evento);
            }
        }
        dados.proximoPedido = proximoPedidoAtual;
        dados.proximoEvento = proximoEventoAtual;
        return dados;
    }

    private static int asInt(Object obj){
        if(obj instanceof Number){
            return ((Number)obj).intValue();
        }
        if(obj instanceof String){
            try{
                return Integer.parseInt((String)obj);
            }catch(NumberFormatException ex){
                return 0;
            }
        }
        return 0;
    }

    private static double asDouble(Object obj){
        if(obj instanceof Number){
            return ((Number)obj).doubleValue();
        }
        if(obj instanceof String){
            try{
                return Double.parseDouble((String)obj);
            }catch(NumberFormatException ex){
                return 0.0;
            }
        }
        return 0.0;
    }

    private static String asString(Object obj){
        if(obj == null){
            return null;
        }
        return String.valueOf(obj);
    }

    private static class DadosCarregados{
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        int proximoPedido = 1;
        int proximoEvento = 1;
    }

    private static class SimpleJsonParser{
        private final String texto;
        private int pos = 0;

        SimpleJsonParser(String texto){
            this.texto = texto;
        }

        Object parseValue(){
            pularEspacos();
            if(pos >= texto.length()){
                return null;
            }
            char c = texto.charAt(pos);
            if(c == '{'){
                return parseObject();
            }
            if(c == '['){
                return parseArray();
            }
            if(c == '"'){
                return parseString();
            }
            if(c == 't' || c == 'f'){
                return parseBoolean();
            }
            if(c == 'n'){
                return parseNull();
            }
            return parseNumber();
        }

        private Map<String,Object> parseObject(){
            Map<String,Object> map = new HashMap<String,Object>();
            pos++;
            pularEspacos();
            if(pos < texto.length() && texto.charAt(pos) == '}'){
                pos++;
                return map;
            }
            while(pos < texto.length()){
                pularEspacos();
                String chave = parseString();
                pularEspacos();
                consumir(':');
                Object valor = parseValue();
                map.put(chave, valor);
                pularEspacos();
                if(pos < texto.length() && texto.charAt(pos) == ','){
                    pos++;
                    continue;
                }
                if(pos < texto.length() && texto.charAt(pos) == '}'){
                    pos++;
                    break;
                }
            }
            return map;
        }

        private List<Object> parseArray(){
            List<Object> lista = new ArrayList<Object>();
            pos++;
            pularEspacos();
            if(pos < texto.length() && texto.charAt(pos) == ']'){
                pos++;
                return lista;
            }
            while(pos < texto.length()){
                Object valor = parseValue();
                lista.add(valor);
                pularEspacos();
                if(pos < texto.length() && texto.charAt(pos) == ','){
                    pos++;
                    continue;
                }
                if(pos < texto.length() && texto.charAt(pos) == ']'){
                    pos++;
                    break;
                }
            }
            return lista;
        }

        private String parseString(){
            consumir('"');
            StringBuilder sb = new StringBuilder();
            while(pos < texto.length()){
                char c = texto.charAt(pos);
                if(c == '"'){
                    pos++;
                    break;
                }
                if(c == '\\'){
                    pos++;
                    if(pos >= texto.length()){
                        break;
                    }
                    char prox = texto.charAt(pos);
                    if(prox == '"' || prox == '\\' || prox == '/'){
                        sb.append(prox);
                    }else if(prox == 'b'){
                        sb.append('\b');
                    }else if(prox == 'f'){
                        sb.append('\f');
                    }else if(prox == 'n'){
                        sb.append('\n');
                    }else if(prox == 'r'){
                        sb.append('\r');
                    }else if(prox == 't'){
                        sb.append('\t');
                    }else if(prox == 'u' && pos + 4 < texto.length()){
                        String hex = texto.substring(pos + 1, pos + 5);
                        sb.append((char)Integer.parseInt(hex, 16));
                        pos += 4;
                    }else{
                        sb.append(prox);
                    }
                }else{
                    sb.append(c);
                }
                pos++;
            }
            return sb.toString();
        }

        private Object parseNumber(){
            int inicio = pos;
            while(pos < texto.length()){
                char c = texto.charAt(pos);
                if((c >= '0' && c <= '9') || c == '-' || c == '+' || c == '.' || c == 'e' || c == 'E'){
                    pos++;
                }else{
                    break;
                }
            }
            String numero = texto.substring(inicio, pos);
            if(numero.indexOf('.') >= 0 || numero.indexOf('e') >= 0 || numero.indexOf('E') >= 0){
                try{
                    return Double.parseDouble(numero);
                }catch(NumberFormatException ex){
                    return 0.0;
                }
            }
            try{
                return Long.parseLong(numero);
            }catch(NumberFormatException ex){
                return 0L;
            }
        }

        private Boolean parseBoolean(){
            if(texto.startsWith("true", pos)){
                pos += 4;
                return Boolean.TRUE;
            }
            if(texto.startsWith("false", pos)){
                pos += 5;
                return Boolean.FALSE;
            }
            return Boolean.FALSE;
        }

        private Object parseNull(){
            if(texto.startsWith("null", pos)){
                pos += 4;
            }
            return null;
        }

        private void consumir(char esperado){
            if(pos < texto.length() && texto.charAt(pos) == esperado){
                pos++;
            }
        }

        private void pularEspacos(){
            while(pos < texto.length()){
                char c = texto.charAt(pos);
                if(c == ' ' || c == '\n' || c == '\r' || c == '\t'){
                    pos++;
                    continue;
                }
                break;
            }
        }
    }
}
