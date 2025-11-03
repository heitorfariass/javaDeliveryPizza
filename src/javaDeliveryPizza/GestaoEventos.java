package javaDeliveryPizza;

import java.util.ArrayList;
import java.util.Scanner;

public class GestaoEventos {

    public static Evento buscarPorId(ArrayList<Evento> eventos, int id){
        for(int i=0;i<eventos.size();i++){
            Evento ev = eventos.get(i);
            if(ev.getId() == id){
                return ev;
            }
        }
        return null;
    }

    public static boolean atualizarEvento(Scanner sc, ArrayList<Evento> eventos){
        if(eventos.size() == 0){
            System.out.println("Não há eventos cadastrados.");
            return false;
        }

        System.out.print("ID do evento: ");
        int id = EntradaDados.lerInteiro(sc);
        Evento evento = buscarPorId(eventos, id);
        if(evento == null){
            System.out.println("Evento não encontrado.");
            return false;
        }

        System.out.println("Status atual: " + evento.getStatus());
        System.out.println("1-AGENDADO  2-REALIZADO  3-CANCELADO");
        System.out.print("Novo status: ");
        int st = EntradaDados.lerInteiro(sc);

        if(st == 1){
            evento.setStatus("AGENDADO");
            evento.setIngressosVendidos(0);
            evento.setAvaliacao(0);
            evento.setPublicoReal(0);
        } else if(st == 2){
            evento.setStatus("REALIZADO");
            System.out.print("Ingressos vendidos/público presente: ");
            int publico = EntradaDados.lerInteiro(sc);
            if(publico > evento.getCapacidade()){
                publico = evento.getCapacidade();
            }
            evento.setIngressosVendidos(publico);
            evento.setPublicoReal(publico);

            System.out.print("Avaliação (1..5) ou 0: ");
            int avaliacao = EntradaDados.lerInteiro(sc);
            evento.setAvaliacao(avaliacao);
        } else if(st == 3){
            evento.setStatus("CANCELADO");
            evento.setIngressosVendidos(0);
            evento.setPublicoReal(0);
            evento.setAvaliacao(0);
        } else {
            System.out.println("Status inválido.");
            return false;
        }
        System.out.println("Evento atualizado.");
        return true;
    }

    public static boolean removerEvento(ArrayList<Evento> eventos, int id){
        for(int i=0;i<eventos.size();i++){
            if(eventos.get(i).getId() == id){
                eventos.remove(i);
                return true;
            }
        }
        return false;
    }
}
