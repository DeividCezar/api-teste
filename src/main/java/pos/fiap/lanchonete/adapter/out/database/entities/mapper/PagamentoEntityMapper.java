package pos.fiap.lanchonete.adapter.out.database.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pos.fiap.lanchonete.adapter.out.database.entities.PagamentoEntity;
import pos.fiap.lanchonete.domain.model.DadosPagamento;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PagamentoEntityMapper {

    @Mapping(target = "pedidoEntity", source = "dadosPagamento.dadosPedido")
    @Mapping(target = "qrCode", source = "dadosPagamento.qrCode")
    @Mapping(target = "qrCodeId", source = "dadosPagamento.qrCodeId")
    PagamentoEntity toEntity(DadosPagamento dadosPagamento);

    @Mapping(target = "dadosPedido", source = "pagamentoEntity.pedidoEntity")
    DadosPagamento toDadosPagamento(PagamentoEntity pagamentoEntity);

}