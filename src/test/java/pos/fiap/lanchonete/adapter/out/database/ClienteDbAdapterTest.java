package pos.fiap.lanchonete.adapter.out.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.adapter.out.database.entities.ClienteEntity;
import pos.fiap.lanchonete.adapter.out.database.entities.mapper.ClienteEntityMapper;
import pos.fiap.lanchonete.adapter.out.database.repository.ClienteRepository;
import pos.fiap.lanchonete.domain.model.entity.Cliente;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.objectmother.entities.ClienteEntityObjectMother.getClienteEntityMock;
import static pos.fiap.lanchonete.objectmother.model.ClienteObjectMother.getClienteMock;

@ExtendWith(MockitoExtension.class)
class ClienteDbAdapterTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Spy
    private ClienteEntityMapper clienteEntityMapper = Mappers.getMapper(ClienteEntityMapper.class);
    @InjectMocks
    private ClienteDbAdapter clienteDbAdapter;

    @Test
    void testCadastrarCliente_Success() {
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(getClienteEntityMock());

        var cliente = clienteDbAdapter.cadastrarCliente(getClienteMock());

        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
        verify(clienteEntityMapper, times(1)).toEntity(any(Cliente.class));
        verify(clienteEntityMapper, times(1)).toCliente(any(ClienteEntity.class));
        assertNotNull(cliente);
    }

    @Test
    void testProcurarClientePorCpf_Success() {
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(getClienteEntityMock()));

        var cliente = clienteDbAdapter.procurarClientePorCpf(getClienteMock().getCpf());

        verify(clienteRepository, times(1)).findById(anyString());
        verify(clienteEntityMapper, times(1)).toCliente(any(ClienteEntity.class));
        assertNotNull(cliente);
        assertTrue(cliente.isPresent());
        assertNotNull(cliente.get());
    }

    @Test
    void testProcurarClientePorCpf_NotPresent() {
        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());

        var cliente = clienteDbAdapter.procurarClientePorCpf(getClienteMock().getCpf());

        verify(clienteRepository, times(1)).findById(anyString());
        verify(clienteEntityMapper, times(0)).toCliente(any(ClienteEntity.class));
        assertNotNull(cliente);
        assertFalse(cliente.isPresent());
    }
}
