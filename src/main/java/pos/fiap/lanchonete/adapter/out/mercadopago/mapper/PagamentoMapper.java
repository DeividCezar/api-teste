package pos.fiap.lanchonete.adapter.out.mercadopago.mapper;

import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPRequestDto;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.domain.model.DadosPedido;
import pos.fiap.lanchonete.domain.model.DadosProduto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static pos.fiap.lanchonete.domain.enums.CategoriaEnum.*;
import static pos.fiap.lanchonete.domain.enums.UnidadeMedidaEnum.UNIT;

public interface PagamentoMapper {

    static PagamentoMPRequestDto buildPaymentRequest(DadosPagamento dadosPagamento) {
        return PagamentoMPRequestDto.builder()
                .title(String.format("Payment for order %s", dadosPagamento.getDadosPedido().getNumeroPedido()))
                .description(String.format("Payment for order %s placed %s", dadosPagamento.getDadosPedido().getNumeroPedido(), dadosPagamento.getDadosPedido().getDataCriacao()))
                .externalReference(dadosPagamento.getDadosPedido().getNumeroPedido())
                .items(buildItemsPayment(dadosPagamento.getDadosPedido()))
                .totalAmount(BigDecimal.valueOf(dadosPagamento.getDadosPedido().getValorTotal()))
                .build();
    }

    private static List<PagamentoMPRequestDto.ItemPagamentoMPRequestDto> buildItemsPayment(DadosPedido dadosPedido) {
        var itens = new ArrayList<PagamentoMPRequestDto.ItemPagamentoMPRequestDto>();
        var itensPedido = dadosPedido.getItens();

        itens.addAll(itensPedido.stream().filter(i -> i.getCategoria().equals(LANCHE))
                .map(PagamentoMapper::buildItem)
                .toList());

        itens.addAll(itensPedido.stream().filter(i -> i.getCategoria().equals(BEBIDA))
                .map(PagamentoMapper::buildItem)
                .toList());

        itens.addAll(itensPedido.stream().filter(i -> i.getCategoria().equals(ACOMPANHAMENTO))
                .map(PagamentoMapper::buildItem)
                .toList());

        return itens;
    }

    private static PagamentoMPRequestDto.ItemPagamentoMPRequestDto buildItem(DadosProduto dadosProduto) {
        return PagamentoMPRequestDto.ItemPagamentoMPRequestDto.builder()
                .title(dadosProduto.getNome())
                .description(dadosProduto.getDescricao())
                .category(dadosProduto.getCategoria().name())
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(dadosProduto.getPreco()))
                .unitMeasure(UNIT.name())
                .totalAmount(BigDecimal.valueOf(dadosProduto.getPreco()))
                .build();
    }
}
