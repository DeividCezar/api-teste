package pos.fiap.lanchonete.objectmother.dtos.in.webhook;

import pos.fiap.lanchonete.adapter.in.api.webhook.dto.WebHookRequestDto;

public class WebHookRequestDtoObjectMother {

    public static WebHookRequestDto getWebHookRequestDtoMock() {
        return WebHookRequestDto.builder()
                .resource("teste/merchant_orders/1234")
                .topic("214")
                .build();
    }

    public static WebHookRequestDto getWebHookRequestDtoSemDadosMock() {
        return WebHookRequestDto.builder()
                .resource("teste")
                .topic("214")
                .build();
    }

}
