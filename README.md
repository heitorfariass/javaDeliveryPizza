# 🍕 Pizzaria & Eventos - Semana Vida Plena 
Projeto da disciplina de Programação Orientada a Objetos.
Sistema em terminal para gestão integrada de delivery de pizzas e eventos (agendamento, buffet e ingressos), com relatórios de desempenho.

<img width="1024" height="1536" alt="e39b22cf-4bf8-4acb-986c-ce9ad0a883d7" src="https://github.com/user-attachments/assets/ac6ce5fd-d5a4-415f-8a03-639f0fd59e88" />

## 🗺️ Instruções de execução:
Para compilar e executar:
1. Instale o Java 17 ou superior.
2. Compile o projeto com:
   javac -d out $(find src -name "*.java")
3. Execute com:
   java -cp out javaDeliveryPizza.app.Main

## 📌 Descrição:
O sistema apoia a tomada de decisão do restaurante (salão e espaço para eventos) ao permitir:
1. Gerenciar pedidos do delivery (itens, status, avaliação, distância, motivo de cancelamento)
2. Administrar eventos (capacidade, público, preço de ingresso, buffet próprio)
3. Gerar relatórios completos de delivery, eventos e visão integrada

Tudo operando via menu em terminal, com fluxo separado para funcionários e proprietário (área restrita com senha).

## ❓ Perguntas que agregam valor:
1. Qual o ticket médio somente do salão, somente dos buffets de eventos realizados e o combinado dos dois?
2. Qual produto do restaurante foi mais consumido no salão, nos eventos e no combinado em cada dia da semana?
3. Quais os três sabores de pizza mais servidos somando salão e eventos?
4. Entre os eventos realizados, quantos contrataram buffet do restaurante e qual receita média gerada?
5. Qual foi o faturamento do salão nos dias em que houveram eventos?
6. Qual a satisfação média do salão, dos eventos e a visão combinada?
7. Qual evento gerou a maior receita de buffet do restaurante?
8. Qual é a receita combinada por dia da semana e como ela se divide entre salão e eventos?
9. Quanto o restaurante faturou ao combinar salão e eventos?

## 💻 Programa em execução:
1. Algumas perguntas sendo respondidas:
<img width="984" height="623" alt="image" src="https://github.com/user-attachments/assets/4cb2036f-ad47-4c4a-b0c4-935788a420a8" />

2. Cadastro de evento:
<img width="982" height="583" alt="image" src="https://github.com/user-attachments/assets/958f2197-3e65-4369-ba95-9fe5f9511dd6" />



## Diagrama UML:
<img width="1373" height="1129" alt="projeto2JavaPOO_2 drawio (1)" src="https://github.com/user-attachments/assets/7cccd4bd-8dd4-4e07-8459-4d9f75faa906" />

## 📦 Pacotes do código:
- `javaDeliveryPizza.app`: ponto de entrada e fluxo de menus.
- `javaDeliveryPizza.domain`: modelos de domínio (pedidos, produtos, eventos).
- `javaDeliveryPizza.service`: regras de negócio, cadastros e relatórios.
- `javaDeliveryPizza.util`: utilitários de entrada, cálculos e listagens.

## Integrantes:
- Heitor Farias - [LinkedIn](https://www.linkedin.com/in/heitorfariassantos/)
- Nina Lira
- Matheus Souto
