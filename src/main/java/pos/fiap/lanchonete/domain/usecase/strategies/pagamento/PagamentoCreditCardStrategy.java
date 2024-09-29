package pos.fiap.lanchonete.domain.usecase.strategies.pagamento;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.MercadoPagoPort;
import pos.fiap.lanchonete.port.PagamentoDbAdapterPort;

import static pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum.AGUARDANDO;

@Component
@RequiredArgsConstructor
public class PagamentoCreditCardStrategy implements PagamentoStrategy {
    private final MercadoPagoPort mercadoPagoPort;
    private final PagamentoDbAdapterPort pagamentoDbAdapterPort;

    @Override
    public DadosPagamento processarPagamento(DadosPagamento dadosPagamento) {
        final var qrCodeData = mercadoPagoPort.gerarPagamentoQRCode(dadosPagamento);
        dadosPagamento.completarPagamentoComQrCode(qrCodeData);
        dadosPagamento.setStatusPagamento(AGUARDANDO);

        return pagamentoDbAdapterPort.salvarPagamento(dadosPagamento);
    }

    @Override
    public void atualizarPagamento(DadosPagamento dadosPagamento) {
        pagamentoDbAdapterPort.salvarPagamento(dadosPagamento);
    }
}
