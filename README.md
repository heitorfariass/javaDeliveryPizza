# Pizzaria Delivery - Semana Vida Plena
Projeto 1 da disciplina de Programação Orientada a Objetos.
Sistema de restaurante focado em delivery de pizzas, com relatórios de vendas, cancelamentos e satisfação.

<img width="1024" height="1024" alt="logotipo" src="https://github.com/user-attachments/assets/1d481794-63a8-47b5-94d9-3d952653e93a" />


## Integrantes:
- Heitor Farias
- Nina Lira
- Matheus Souto
- Dário Eduardo

## Instruções de execução:
Para compilar e executar:
1. Instale o Java 17 ou superior.
2. Compile o projeto com (gera classes na pasta `out/`):
   javac -d out src/javaDeliveryPizza/*.java
3. Execute com:
   java -cp out javaDeliveryPizza.Main
4. No VS Code, certifique-se de que a classe principal configurada seja `javaDeliveryPizza.Main` (o atalho "Run Java" no arquivo `Main.java` já faz isso automaticamente).

### Banco de dados simples (JSON)
- Todos os pedidos e eventos ficam salvos no arquivo `dados.json`, criado na mesma pasta do projeto.
- Sempre que você cadastrar, atualizar ou remover registros pelo menu, o arquivo é atualizado automaticamente.
- O menu do proprietário possui duas opções extras:
  * **"Mostrar modelo JSON para ChatGPT"**: imprime no terminal um exemplo pronto para pedir dados fictícios ao ChatGPT.
  * **"Importar dados fictícios via JSON"**: permite colar, diretamente no terminal, um JSON gerado pelo ChatGPT (digite `FIM` em uma linha separada para finalizar a colagem). Novos pedidos/eventos são adicionados com numeração automática, evitando conflitos.
- Você também pode editar `dados.json` manualmente; ids iguais ou vazios são renumerados ao carregar o sistema.

## Descrição:
O sistema tem como objetivo facilitar a tomada de decisão da pizzaria,
oferencendo ferramentas para: gerenciar pedidos de pizzas, acompanhar relatórios de vendas, 
motivos de cancelamento, distâncias de entrega e satisfação dos clientes. 

## Diagrama UML:
<img width="682" height="982" alt="Diagrama sem nome drawio" src="https://github.com/user-attachments/assets/e0608d12-ebae-4a7c-9f05-057bc2170b25" />
