package pos.fiap.lanchonete.objectmother.dtos.in.produto;

import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoRequestDto;

import static pos.fiap.lanchonete.domain.enums.CategoriaEnum.ACOMPANHAMENTO;

public class ProdutoRequestDtoObjectMother {

    public static ProdutoRequestDto getProdutoRequestDtoMock() {
        return ProdutoRequestDto.builder()
                .id("123")
                .nome("Produto Teste")
                .categoria(ACOMPANHAMENTO)
                .preco(Double.valueOf("100.00"))
                .descricao("Produto de teste")
                .build();
    }
}
