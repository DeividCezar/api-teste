package pos.fiap.lanchonete.objectmother.dtos.in.pedido;

import pos.fiap.lanchonete.adapter.in.api.pedido.dto.PedidoRequestDto;

import java.util.List;

import static pos.fiap.lanchonete.objectmother.dtos.in.produto.ProdutoRequestDtoObjectMother.getProdutoRequestDtoMock;

public class PedidoRequestDtoObjectMother {

    public static PedidoRequestDto getPedidoRequestDtoMock() {
        return PedidoRequestDto.builder()
                .cpfCliente("12345648")
                .itens(List.of(getProdutoRequestDtoMock()))
                .build();
    }
}
