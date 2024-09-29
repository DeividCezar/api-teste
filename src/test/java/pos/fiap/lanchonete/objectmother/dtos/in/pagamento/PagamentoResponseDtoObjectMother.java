package pos.fiap.lanchonete.objectmother.dtos.in.pagamento;

import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoResponseDto;

import static pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum.APROVADO;

public class PagamentoResponseDtoObjectMother {

    public static PagamentoResponseDto getPagamentoAprovadoResponseDtoMock() {
        return PagamentoResponseDto.builder()
                .statusPagamento(APROVADO)
                .qrCode("123")
                .build();
    }
}
