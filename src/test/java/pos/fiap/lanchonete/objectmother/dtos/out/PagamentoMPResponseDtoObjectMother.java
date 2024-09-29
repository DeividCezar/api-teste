package pos.fiap.lanchonete.objectmother.dtos.out;

import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;

public class PagamentoMPResponseDtoObjectMother {

    public static PagamentoMPResponseDto getPagamentoMPResponseDtoMock() {
        return PagamentoMPResponseDto.builder()
                .id("123")
                .externalReference("teste")
                .dateApproved("")
                .status("")
                .statusDetail("")
                .inStoreOrderId("d4e8ca59-3e1d-4c03-b1f6-580e87c654ae")
                .qrData("00020101021243650016COM.MERCADOLIBRE02013063638f1192a-5fd1-4180-a180-8bcae3556bc35204000053039865802BR5925IZABEL AAAA DE MELO6007BARUERI62070503***63040B6D")
                .build();
    }
}
