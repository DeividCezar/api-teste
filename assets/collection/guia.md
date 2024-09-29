## Guia para execução das APIs

Segue abaixo instruções com a ordem de execução das APIs.

### 1. Cliente

#### 1.1 Cadastrar cliente

Inicialmente, deve-se cadastrar o cliente:

[POST] */api/v1/cliente*

```json
{
  "cpf": "1001",
  "nome": "Paulo",
  "email": "paulo@gmail.com"
}
```

#### 1.2 Consultar cliente

É possível consultar o cliente pelo cpf:

[GET] */api/v1/cliente/{cpf}*

### 2. Produto

#### 2.1 Cadastrar produto

Na sequência, deve-se cadastrar os produtos:

[POST] */api/v1/produto*

```json
{
  "nome": "X-bacon",
  "categoria": "LANCHE",
  "preco": 10,
  "descricao": "Pão, carne, queijo e bacon"
}
```

#### 2.2 Consultar produtos pela categoria

É possível consultar os produtos pela categoria:

[GET] */api/v1/produto/{categoria}*

#### 2.3 Editar produto

É possível editar um produto com o seu id:

[PUT] */api/v1/produto/{id}*

```json
{
  "nome": "X-bacon",
  "categoria": "LANCHE",
  "preco": 10,
  "descricao": "Pão, duas carnes, queijo e bacon"
}
```

#### 2.4 Remover produto

É possível remover um produto com o seu id:

[DEL] */api/v1/produto/{id}*

### 3. Pedido

#### 3.1 Cadastrar pedido

O próximo passo é cadastrar o pedido do cliente:

[POST] */api/v1/pedido*

```json
{
  "cpfCliente": "1001",
  "itens": [
    {
      "id": "00ed9ed1-1dd3-45ab-9bf2-645825949b23",
      "nome": "X-bacon",
      "categoria": "LANCHE",
      "preco": 10.0,
      "descricao": "Pão, carne, queijo e bacon",
      "imagem": null
    }
  ]
}
```

#### 3.2 Listar pedidos

É possível listar os pedidos:

[GET] */api/v1/pedido*

#### 3.3 Atualizar pedido

Com o andamento do processo, deve-se atualizar o status do pedido:

[PATCH] */api/v1/pedido/{idPedido}*

```json
{
  "statusPedido": "PRONTO"
}
```

### 4. Pagamento

#### 4.1 Pagar pedido

No fechamento da compra o cliente deve pagar o pedido:

[POST] */api/v1/pagamento*

```json
{
  "numeroPedido": "a23bb9c7-c319-4694-9754-9573b9b8bf26",
  "metodoPagamento": "PIX"
}
```

#### 4.2 Consultar pagamento do pedido

É possível consultar o pagamento do pedido com seu id:

[GET] */api/v1/pagamento/{idPedido}*





