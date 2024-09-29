package pos.fiap.lanchonete.objectmother.model;

import pos.fiap.lanchonete.domain.model.DadosPagamento;

import static pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum.APROVADO;
import static pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum.PIX;
import static pos.fiap.lanchonete.objectmother.dtos.out.PagamentoMPResponseDtoObjectMother.getPagamentoMPResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosPedidoObjectMother.getDadosPedidoMock;

public class DadosPagamentoObjectMother {

    public static DadosPagamento getDadosPagamentoMock() {
        var pagamentoMPResponseDtoMock = getPagamentoMPResponseDtoMock();

        return DadosPagamento.builder()
                .id("123")
                .statusPagamento(APROVADO)
                .metodoPagamento(PIX)
                .dadosPedido(getDadosPedidoMock())
                .qrCode(pagamentoMPResponseDtoMock.getQrData())
                .qrCodeId(pagamentoMPResponseDtoMock.getId())
                .build();
    }
}
