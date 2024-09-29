package pos.fiap.lanchonete.domain.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.usecase.strategies.pagamento.PagamentoContext;
import pos.fiap.lanchonete.port.PagamentoDbAdapterPort;
import pos.fiap.lanchonete.port.PedidoUseCasePort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.objectmother.model.DadosPagamentoObjectMother.getDadosPagamentoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosPedidoObjectMother.getDadosPedidoMock;

@ExtendWith(MockitoExtension.class)
class PagamentoUseCaseTest {
    @Mock
    private PagamentoDbAdapterPort pagamentoDbAdapterPort;
    @Mock
    private PedidoUseCasePort pedidoUseCasePort;
    @Mock
    private PagamentoContext pagamentoContext;
    @InjectMocks
    private PagamentoUseCase pagamentoUseCase;

    @Test
    void obterDadosPagamento_whenSendIdPedido_thenSucceed() {
        when(pagamentoDbAdapterPort.obterDadosPagamento(anyString())).thenReturn(getDadosPagamentoMock());

        var dadosPagamento = pagamentoUseCase.obterDadosPagamento("2");

        assertNotNull(dadosPagamento);
        verify(pagamentoDbAdapterPort, times(1)).obterDadosPagamento(anyString());
    }

    @Test
    void processarPagamento_whenSendDadosPagamento_thenSucceed() {
        when(pedidoUseCasePort.obterPedidoPorId(anyString())).thenReturn(getDadosPedidoMock());
        when(pagamentoContext.processarPagamento(any(DadosPagamento.class))).thenReturn(getDadosPagamentoMock());

        var dadosPagamento = pagamentoUseCase.processarPagamento(getDadosPagamentoMock());

        assertNotNull(dadosPagamento);
        verify(pagamentoContext, times(1)).processarPagamento(any(DadosPagamento.class));
    }

    @Test
    void atualizarPagamento_whenSendDadosPagamento_thenSucceed() {
        doNothing().when(pagamentoContext).atualizarPagamento(any(DadosPagamento.class));

        pagamentoUseCase.atualizarPagamento(getDadosPagamentoMock());

        verify(pagamentoContext, times(1)).atualizarPagamento(any(DadosPagamento.class));
    }
}
