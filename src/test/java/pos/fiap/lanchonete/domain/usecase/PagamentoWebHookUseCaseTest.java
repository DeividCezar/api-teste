package pos.fiap.lanchonete.domain.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;
import pos.fiap.lanchonete.port.PedidoUseCasePort;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.objectmother.model.DadosPagamentoObjectMother.getDadosPagamentoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosPedidoObjectMother.getDadosPedidoMock;

@ExtendWith(MockitoExtension.class)
class PagamentoWebHookUseCaseTest {
    @Mock
    private PedidoUseCasePort pedidoUseCasePort;
    @Mock
    private MercadoPagoPort mercadoPagoPort;
    @Mock
    private PagamentoUseCasePort pagamentoUseCasePort;
    @InjectMocks
    private PagamentoWebHookUseCase pagamentoWebHookUseCase;

    @Test
    void processarPagamento_whenSendMerchantOrderId_thenSucceed() {
        when(mercadoPagoPort.obterNumeroPedido(anyString())).thenReturn("123");
        when(pedidoUseCasePort.obterPedidoPorId(anyString())).thenReturn(getDadosPedidoMock());
        when(pagamentoUseCasePort.obterDadosPagamento(anyString())).thenReturn(getDadosPagamentoMock());
        doNothing().when(pagamentoUseCasePort).atualizarPagamento(any(DadosPagamento.class));

        pagamentoWebHookUseCase.processarPagamento("2");

        verify(mercadoPagoPort, times(1)).obterNumeroPedido(anyString());
        verify(pedidoUseCasePort, times(1)).obterPedidoPorId(anyString());
        verify(pagamentoUseCasePort, times(1)).atualizarPagamento(any(DadosPagamento.class));
    }
}
