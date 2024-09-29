package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;
import pos.fiap.lanchonete.port.PagamentoDbAdapterPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.objectmother.dtos.out.PagamentoMPResponseDtoObjectMother.getPagamentoMPResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosPagamentoObjectMother.getDadosPagamentoMock;

@ExtendWith(MockitoExtension.class)
class PagamentoCreditCardStrategyTest {
    @Mock
    private MercadoPagoPort mercadoPagoPort;
    @Mock
    private PagamentoDbAdapterPort pagamentoDbAdapterPort;
    @InjectMocks
    private PagamentoCreditCardStrategy pagamentoCreditCardStrategy;

    @Test
    void processarPagamento_whenSendDadosPagamento_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        when(mercadoPagoPort.gerarPagamentoQRCode(any(DadosPagamento.class)))
                .thenReturn(getPagamentoMPResponseDtoMock());
        when(pagamentoDbAdapterPort.salvarPagamento(any(DadosPagamento.class))).thenReturn(dadosPagamento);

        var retorno = pagamentoCreditCardStrategy.processarPagamento(dadosPagamento);

        assertNotNull(retorno);
        verify(mercadoPagoPort, times(1)).gerarPagamentoQRCode(any(DadosPagamento.class));
        verify(pagamentoDbAdapterPort, times(1)).salvarPagamento(any(DadosPagamento.class));
    }

    @Test
    void atualizarPagamento_whenSendDadosPagamento_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        when(pagamentoDbAdapterPort.salvarPagamento(any(DadosPagamento.class))).thenReturn(dadosPagamento);

        pagamentoCreditCardStrategy.atualizarPagamento(dadosPagamento);

        verify(pagamentoDbAdapterPort, times(1)).salvarPagamento(any(DadosPagamento.class));
    }
}
