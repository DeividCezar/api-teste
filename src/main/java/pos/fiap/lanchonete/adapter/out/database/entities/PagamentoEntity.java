package pos.fiap.lanchonete.adapter.out.database.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;
import pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

import java.time.LocalDateTime;

import static pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum.AGUARDANDO;

@Data
@Entity(name = "tb_pagamento")
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private StatusPagamentoEnum statusPagamento;

    @OneToOne
    private PedidoEntity pedidoEntity;
    private String qrCode;
    private String qrCodeId;
    private MetodoPagamentoEnum metodoPagamento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public PagamentoEntity() {
        this.statusPagamento = AGUARDANDO;
        this.dataCriacao = LocalDateTime.now();
    }

    @Builder
    public PagamentoEntity(StatusPagamentoEnum statusPagamento, PedidoEntity pedidoEntity, String qrCode, String qrCodeId,
                           MetodoPagamentoEnum metodoPagamento) {
        this.statusPagamento = statusPagamento;
        this.pedidoEntity = pedidoEntity;
        this.qrCode = qrCode;
        this.qrCodeId = qrCodeId;
        this.metodoPagamento = metodoPagamento;
        this.dataCriacao = LocalDateTime.now();
    }

    public void atualizarDadosEntity(DadosPagamento dadosPagamento, PedidoEntity pedidoEntity) {
        this.statusPagamento = dadosPagamento.getStatusPagamento();
        this.pedidoEntity = pedidoEntity;
        this.qrCode = dadosPagamento.getQrCode();
        this.qrCodeId = dadosPagamento.getQrCodeId();
        this.metodoPagamento = dadosPagamento.getMetodoPagamento();
        this.dataAtualizacao = LocalDateTime.now();
    }
}