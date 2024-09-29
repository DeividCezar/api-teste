package pos.fiap.lanchonete.adapter.out.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pos.fiap.lanchonete.adapter.out.database.entities.PagamentoEntity;
import pos.fiap.lanchonete.adapter.out.database.entities.mapper.PagamentoEntityMapper;
import pos.fiap.lanchonete.adapter.out.database.repository.PagamentoRepository;
import pos.fiap.lanchonete.adapter.out.database.repository.PedidoRepository;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.PagamentoDbAdapterPort;

import static java.lang.String.format;
import static pos.fiap.lanchonete.utils.Constantes.FIM;
import static pos.fiap.lanchonete.utils.Constantes.INICIO;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoDbAdapter implements PagamentoDbAdapterPort {

    private static final String SERVICE_NAME = "PagamentoDbAdapter";
    private static final String BUSCAR_STATUS_PAGAMENTO_METHOD_NAME = "buscarStatusPagamento";
    private static final String SALVAR_PAGAMENTO_METHOD_NAME = "salvarPagamento";
    private static final String STRING_LOG_FORMAT = "%s_%s_%s {}";

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoEntityMapper pagamentoEntityMapper;

    @Override
    public DadosPagamento obterDadosPagamento(String idPedido) {
        log.info(format(STRING_LOG_FORMAT, SERVICE_NAME, BUSCAR_STATUS_PAGAMENTO_METHOD_NAME, INICIO), idPedido);

        var pedido = pedidoRepository.findById(idPedido);

        if (pedido.isEmpty()) {
            log.info(format(STRING_LOG_FORMAT, SERVICE_NAME, BUSCAR_STATUS_PAGAMENTO_METHOD_NAME, "_ERROR"), idPedido);
            return null;
        }

        var pagamentoEntity = pagamentoRepository.findByPedidoEntity(pedido.get());

        if (pagamentoEntity.isPresent()) {
            log.info(format(STRING_LOG_FORMAT, SERVICE_NAME, BUSCAR_STATUS_PAGAMENTO_METHOD_NAME, FIM), idPedido);
            return pagamentoEntityMapper.toDadosPagamento(pagamentoEntity.get());
        } else {
            return null;
        }
    }

    @Override
    public DadosPagamento salvarPagamento(DadosPagamento dadosPagamento) {
        log.info(format(STRING_LOG_FORMAT, SERVICE_NAME, SALVAR_PAGAMENTO_METHOD_NAME, INICIO), dadosPagamento);
        var pedido = pedidoRepository.findById(dadosPagamento.getDadosPedido().getNumeroPedido());

        if (pedido.isEmpty()) {
            log.info(format(STRING_LOG_FORMAT, SERVICE_NAME, SALVAR_PAGAMENTO_METHOD_NAME, "_ERROR"), dadosPagamento);
            return null;
        }

        var pagamentoEntityFindById = pagamentoRepository.findByPedidoEntity(pedido.get());
        var pagamentoEntity = PagamentoEntity.builder().build();

        if (pagamentoEntityFindById.isPresent()) {
            // TODO Refatorar update
            pagamentoRepository.delete(pagamentoEntityFindById.get());
            pagamentoEntityFindById.get().atualizarDadosEntity(dadosPagamento, pagamentoEntityFindById.get().getPedidoEntity());
            pagamentoEntity = pagamentoEntityFindById.get();
        } else {
            pagamentoEntity = pagamentoEntityMapper.toEntity(dadosPagamento);
        }

        pagamentoEntity = pagamentoRepository.save(pagamentoEntity);
        var pagamento = pagamentoEntityMapper.toDadosPagamento(pagamentoEntity);
        log.info(format(STRING_LOG_FORMAT, SERVICE_NAME, SALVAR_PAGAMENTO_METHOD_NAME, FIM), pagamento);
        return pagamento;
    }
}