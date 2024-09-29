package pos.fiap.lanchonete.objectmother.dtos.in.cliente;

import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteResponseDto;

public class ClienteResponseDtoObjectMother {

    public static ClienteResponseDto getClienteResponseDtoMock() {
        return ClienteResponseDto.builder()
                .cpf("1234")
                .email("teste@teste.com")
                .nome("Fulano")
                .build();
    }
}
