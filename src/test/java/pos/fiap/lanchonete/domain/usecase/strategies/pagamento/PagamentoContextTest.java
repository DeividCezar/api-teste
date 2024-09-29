package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum.CREDIT_CARD;
import static pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum.DEBIT_CARD;
import static pos.fiap.lanchonete.objectmother.model.DadosPagamentoObjectMother.getDadosPagamentoMock;

@ExtendWith(MockitoExtension.class)
class PagamentoContextTest {

    @Mock
    private PagamentoPixStrategy pagamentoPixStrategy;
    @Mock
    private PagamentoDebitCardStrategy pagamentoDebitCardStrategy;
    @Mock
    private PagamentoCreditCardStrategy pagamentoCreditCardStrategy;
    @InjectMocks
    private PagamentoContext pagamentoContext;

    @Test
    void processarPagamento_whenSendDadosPagamentoPIX_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        when(pagamentoPixStrategy.processarPagamento(any(DadosPagamento.class))).thenReturn(dadosPagamento);

        var retorno = pagamentoContext.processarPagamento(dadosPagamento);

        assertNotNull(retorno);
        verify(pagamentoPixStrategy, times(1)).processarPagamento(any(DadosPagamento.class));
    }

    @Test
    void processarPagamento_whenSendDadosPagamentoDebitCard_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        dadosPagamento.setMetodoPagamento(DEBIT_CARD);
        when(pagamentoDebitCardStrategy.processarPagamento(any(DadosPagamento.class))).thenReturn(dadosPagamento);

        var retorno = pagamentoContext.processarPagamento(dadosPagamento);

        assertNotNull(retorno);
        verify(pagamentoDebitCardStrategy, times(1)).processarPagamento(any(DadosPagamento.class));
    }

    @Test
    void processarPagamento_whenSendDadosPagamentoCreditCard_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        dadosPagamento.setMetodoPagamento(CREDIT_CARD);
        when(pagamentoCreditCardStrategy.processarPagamento(any(DadosPagamento.class))).thenReturn(dadosPagamento);

        var retorno = pagamentoContext.processarPagamento(dadosPagamento);

        assertNotNull(retorno);
        verify(pagamentoCreditCardStrategy, times(1)).processarPagamento(any(DadosPagamento.class));
    }

    @Test
    void atualizarPagamento_whenSendDadosPagamentoPIX_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        doNothing().when(pagamentoPixStrategy).atualizarPagamento(any(DadosPagamento.class));

        pagamentoContext.atualizarPagamento(dadosPagamento);

        verify(pagamentoPixStrategy, times(1)).atualizarPagamento(any(DadosPagamento.class));
    }

    @Test
    void atualizarPagamento_whenSendDadosPagamentoDebitCard_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        dadosPagamento.setMetodoPagamento(DEBIT_CARD);
        doNothing().when(pagamentoDebitCardStrategy).atualizarPagamento(any(DadosPagamento.class));

        pagamentoContext.atualizarPagamento(dadosPagamento);

        verify(pagamentoDebitCardStrategy, times(1)).atualizarPagamento(any(DadosPagamento.class));
    }

    @Test
    void atualizarPagamento_whenSendDadosPagamentoCreditCard_thenSucceed() {
        var dadosPagamento = getDadosPagamentoMock();
        dadosPagamento.setMetodoPagamento(CREDIT_CARD);
        doNothing().when(pagamentoCreditCardStrategy).atualizarPagamento(any(DadosPagamento.class));

        pagamentoContext.atualizarPagamento(dadosPagamento);

        verify(pagamentoCreditCardStrategy, times(1)).atualizarPagamento(any(DadosPagamento.class));
    }

}
