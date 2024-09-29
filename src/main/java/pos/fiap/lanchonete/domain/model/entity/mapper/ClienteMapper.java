package pos.fiap.lanchonete.domain.model.entity.mapper;

import org.mapstruct.Mapper;
import pos.fiap.lanchonete.domain.model.DadosCliente;
import pos.fiap.lanchonete.domain.model.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente fromDadosCliente(DadosCliente dadosCliente);

    DadosCliente toDadosCliente(Cliente cliente);
}
