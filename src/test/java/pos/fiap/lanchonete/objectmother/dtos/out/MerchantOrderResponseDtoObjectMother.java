package pos.fiap.lanchonete.objectmother.dtos.out;

import pos.fiap.lanchonete.adapter.out.mercadopago.dto.merchantorder.MerchantOrderResponseDto;

public class MerchantOrderResponseDtoObjectMother {

    public static MerchantOrderResponseDto getMerchantOrderResponseDtoMock(){
        return MerchantOrderResponseDto.builder()
                .externalReference("2123")
                .build();
    }
}
