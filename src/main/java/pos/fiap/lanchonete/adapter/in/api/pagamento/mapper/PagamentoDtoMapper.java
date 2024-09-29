package pos.fiap.lanchonete.adapter.in.api.pagamento.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoResponseDto;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.utils.Constantes;

@Mapper(componentModel = "spring", imports = Constantes.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PagamentoDtoMapper {

    @Mapping(target = "dadosPedido.numeroPedido", source = "numeroPedido")
    DadosPagamento toDadosPagamentoFromPagamentoRequestDto(PagamentoRequestDto pagamentoRequestDto);

    @Mapping(target = "numeroPedido", source = "dadosPedido.numeroPedido")
    PagamentoResponseDto toPagamentoResponseDtoFromDadosPagamento(DadosPagamento dadosPagamento);
}