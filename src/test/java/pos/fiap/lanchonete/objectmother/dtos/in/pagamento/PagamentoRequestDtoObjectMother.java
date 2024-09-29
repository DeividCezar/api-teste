package pos.fiap.lanchonete.objectmother.dtos.in.pagamento;

import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoRequestDto;

import static pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum.PIX;

public class PagamentoRequestDtoObjectMother {

    public static PagamentoRequestDto getPagamentoRequestDtoMock() {
        return PagamentoRequestDto.builder()
                .numeroPedido("123")
                .metodoPagamento(PIX)
                .build();
    }
}
