package pos.fiap.lanchonete.domain.entity.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.domain.model.entity.mapper.ClienteMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pos.fiap.lanchonete.objectmother.model.DadosClienteObjectMother.getDadosClienteMock;

@ExtendWith(MockitoExtension.class)
class ClienteDtoMapperTest {

    @Spy
    private ClienteMapper clienteMapper = Mappers.getMapper(ClienteMapper.class);

    @Test
    void givenCliente_whenSendDadosCliente_thenSucceed() {
        var cliente = clienteMapper.fromDadosCliente(getDadosClienteMock());

        assertNotNull(cliente);
        assertNotNull(cliente.getCpf());
        assertNotNull(cliente.getNome());
        assertNotNull(cliente.getEmail());
    }

    @Test
    void givenNullCliente_whenSendNullDadosCliente_thenSucceed() {
        var cliente = clienteMapper.fromDadosCliente(null);

        assertNull(cliente);
    }
}
