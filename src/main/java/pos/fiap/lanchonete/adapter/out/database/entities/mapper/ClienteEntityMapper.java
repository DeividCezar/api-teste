package pos.fiap.lanchonete.adapter.out.database.entities.mapper;

import org.mapstruct.Mapper;
import pos.fiap.lanchonete.adapter.out.database.entities.ClienteEntity;
import pos.fiap.lanchonete.domain.model.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteEntityMapper {

    ClienteEntity toEntity(Cliente cliente);

    Cliente toCliente(ClienteEntity clienteEntity);
}
