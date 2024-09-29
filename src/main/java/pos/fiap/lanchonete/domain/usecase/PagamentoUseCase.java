package pos.fiap.lanchonete.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.usecase.strategies.pagamento.PagamentoContext;
import pos.fiap.lanchonete.port.PagamentoDbAdapterPort;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;
import pos.fiap.lanchonete.port.PedidoUseCasePort;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagamentoUseCase implements PagamentoUseCasePort {
    private final PagamentoDbAdapterPort pagamentoDbAdapterPort;
    private final PedidoUseCasePort pedidoUseCasePort;
    private final PagamentoContext pagamentoContext;

    @Override
    public DadosPagamento obterDadosPagamento(String idPedido) {
        return pagamentoDbAdapterPort.obterDadosPagamento(idPedido);
    }

    @Override
    @SneakyThrows
    public DadosPagamento processarPagamento(DadosPagamento dadosPagamento) {
        var dadosPedido = pedidoUseCasePort.obterPedidoPorId(dadosPagamento.getDadosPedido().getNumeroPedido());
        dadosPagamento.setDadosPedido(dadosPedido);
        return pagamentoContext.processarPagamento(dadosPagamento);
    }

    @Override
    public void atualizarPagamento(DadosPagamento dadosPagamento) {
        pagamentoContext.atualizarPagamento(dadosPagamento);
    }
}