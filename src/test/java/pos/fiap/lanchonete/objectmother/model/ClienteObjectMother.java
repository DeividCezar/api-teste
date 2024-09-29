package pos.fiap.lanchonete.objectmother.model;

import pos.fiap.lanchonete.domain.model.entity.Cliente;

public class ClienteObjectMother {

    public static Cliente getClienteMock() {
        return Cliente.builder()
                .cpf("1234")
                .nome("Fulano")
                .email("teste@teste.com")
                .build();
    }
}
