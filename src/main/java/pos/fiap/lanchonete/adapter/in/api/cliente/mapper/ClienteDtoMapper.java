package pos.fiap.lanchonete.adapter.in.api.cliente.mapper;

import org.mapstruct.Mapper;
import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteRequestDto;
import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteResponseDto;
import pos.fiap.lanchonete.domain.model.DadosCliente;

@Mapper(componentModel = "spring")
public interface ClienteDtoMapper {

    DadosCliente toDadosClientefromRequestDto(ClienteRequestDto clienteRequestDto);

    ClienteResponseDto toClienteResponseDtoFromDadosCliente(DadosCliente dadosCliente);
}
