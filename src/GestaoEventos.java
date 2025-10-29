package javaDeliveryPizza.src;

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

    public static void atualizarEvento(Scanner sc, ArrayList<Evento> eventos){
        if(eventos.size() == 0){
            System.out.println("Não há eventos cadastrados.");
            return;
        }

        System.out.print("ID do evento: ");
        int id = sc.nextInt();
        Evento evento = buscarPorId(eventos, id);
        if(evento == null){
            System.out.println("Evento não encontrado.");
            return;
        }

        System.out.println("Status atual: " + evento.getStatus());
        System.out.println("1-AGENDADO  2-REALIZADO  3-CANCELADO");
        System.out.print("Novo status: ");
        int st = sc.nextInt();

        if(st == 1){
            evento.setStatus("AGENDADO");
            evento.setIngressosVendidos(0);
            evento.setAvaliacao(0);
            evento.setPublicoReal(0);
        } else if(st == 2){
            evento.setStatus("REALIZADO");
            System.out.print("Ingressos vendidos/público presente: ");
            int publico = sc.nextInt();
            if(publico > evento.getCapacidade()){
                publico = evento.getCapacidade();
            }
            evento.setIngressosVendidos(publico);
            evento.setPublicoReal(publico);

            System.out.print("Avaliação (1..5) ou 0: ");
            int avaliacao = sc.nextInt();
            evento.setAvaliacao(avaliacao);
        } else if(st == 3){
            evento.setStatus("CANCELADO");
            evento.setIngressosVendidos(0);
            evento.setPublicoReal(0);
            evento.setAvaliacao(0);
        } else {
            System.out.println("Status inválido.");
        }
        System.out.println("Evento atualizado.");
    }
}
