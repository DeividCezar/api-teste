## Event Storming

Aplicou-se a técnica do event storming para modelar o processo do negócio.

### Brainstorm

O primeiro passo foi o brainstorm, onde foram levantados os eventos do domínio abordado.

<p align = "center">
  <img src = Brainstorming.svg>
</p>

Em seguida, organizou-se os eventos na linha do tempo para os fluxos de realização do pedido e pagamento e preparação e entrega do pedido.

### Realização do pedido e pagamento:

<p align = "center">
  <img src = Pedido-pagamento.svg>
</p>

### Preparação e entrega do pedido:

<p align = "center">
  <img src = Preparacao-entrega.svg>
</p>

### Dicionário de linguagem ubíqua

*Cliente (Customer)*

Definição: uma pessoa que utiliza o sistema para fazer pedidos.

Atributos: cpf, nome e e-mail.

<br>

*Pedido (Order)*

Definição: os produtos selecionados pelo cliente.

Atributos: número, valor, data e lista de produtos.

<br>

*Produto (Product)*

Definição: qualquer item do cardápio.

Atributos: nome, preço, categoria, descrição e imagem.

<br>

*Lanche (Burger)*

Definição: hambúrgueres do cardápio.

<br>

*Acompanhamento (Side)*

Definição: porções do cardápio.

<br>

*Bebida (Drink)*

Definição: bebidas do cardápio.

<br>

*Sobremesa (Desert)*

Definição: sobremesas do cardápio.

<br>

*Status do pedido (Order status)*

Definição: situação do pedido.

Opções: recebido, em preparação, pronto e finalizado.

<br>

*Status do pagamento (Payment status)*

Definição: situação do pagamento do pedido.

Opções: aguardando, aprovado, recusado.

<br>
