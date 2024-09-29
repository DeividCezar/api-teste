package pos.fiap.lanchonete.objectmother.dtos.in.cliente;

import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteRequestDto;

public class ClienteRequestDtoObjectMother {

    public static ClienteRequestDto getClienteRequestDtoMock() {
        return ClienteRequestDto.builder()
                .cpf("123")
                .email("teste@teste.com")
                .nome("Fulano")
                .build();
    }
}
