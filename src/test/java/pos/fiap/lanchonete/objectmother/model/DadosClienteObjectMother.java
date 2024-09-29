package pos.fiap.lanchonete.objectmother.model;

import pos.fiap.lanchonete.domain.model.DadosCliente;

public class DadosClienteObjectMother {

    public static DadosCliente getDadosClienteMock() {
        return DadosCliente.builder()
                .cpf("123456578940")
                .email("teste@teste.com")
                .nome("Fulano Da Silva 1")
                .build();
    }
}
