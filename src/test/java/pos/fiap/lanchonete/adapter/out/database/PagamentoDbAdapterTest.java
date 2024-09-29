package pos.fiap.lanchonete.adapter.out.database;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.adapter.out.database.entities.PagamentoEntity;
import pos.fiap.lanchonete.adapter.out.database.entities.PedidoEntity;
import pos.fiap.lanchonete.adapter.out.database.entities.mapper.PagamentoEntityMapper;
import pos.fiap.lanchonete.adapter.out.database.repository.PagamentoRepository;
import pos.fiap.lanchonete.adapter.out.database.repository.PedidoRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.objectmother.entities.PagamentoEntityObjectMother.getPagamentoAprovadoEntityMock;
import static pos.fiap.lanchonete.objectmother.entities.PedidoEntityObjectMother.getPedidoEntityMock;

@ExtendWith(MockitoExtension.class)
class PagamentoDbAdapterTest {
    @Mock
    private PagamentoRepository pagamentoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @Spy
    private PagamentoEntityMapper pagamentoEntityMapper = Mappers.getMapper(PagamentoEntityMapper.class);
    @InjectMocks
    private PagamentoDbAdapter pagamentoMongoAdapter;

    @Test
    void testObterDadosPagamento_Success() {
        when(pedidoRepository.findById(anyString())).thenReturn(Optional.ofNullable(getPedidoEntityMock()));
        when(pagamentoRepository.findByPedidoEntity(any(PedidoEntity.class)))
                .thenReturn(Optional.of(getPagamentoAprovadoEntityMock()));

        var pagamento = pagamentoMongoAdapter.obterDadosPagamento("123");

        verify(pagamentoRepository, times(1)).findByPedidoEntity(any(PedidoEntity.class));
        verify(pagamentoEntityMapper, times(1)).toDadosPagamento(any(PagamentoEntity.class));
        assertNotNull(pagamento);
    }
}
