package pos.fiap.lanchonete.adapter.out.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pos.fiap.lanchonete.adapter.out.database.entities.mapper.ClienteEntityMapper;
import pos.fiap.lanchonete.adapter.out.database.repository.ClienteRepository;
import pos.fiap.lanchonete.domain.model.entity.Cliente;
import pos.fiap.lanchonete.port.ClienteAdapterPort;

import java.util.Optional;

import static pos.fiap.lanchonete.utils.Constantes.FIM;
import static pos.fiap.lanchonete.utils.Constantes.INICIO;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteDbAdapter implements ClienteAdapterPort {
    private static final String SERVICE_NAME = "ClienteDbAdapter";
    private static final String CADASTRAR_CLIENTE_METHOD_NAME = "cadastrarCliente";
    private static final String PROCURAR_CLIENTE_POR_CPF_METHOD_NAME = "procurarClientePorCpf";
    private static final String STRING_LOG_FORMAT = "%s_%s_%s {}";
    private final ClienteRepository clienteRepository;
    private final ClienteEntityMapper clienteEntityMapper;

    @Override
    public Cliente cadastrarCliente(Cliente cliente) {
        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, CADASTRAR_CLIENTE_METHOD_NAME, INICIO), cliente);

        var clienteEntity = clienteEntityMapper.toEntity(cliente);
        clienteEntity = clienteRepository.save(clienteEntity);

        var clienteSaved = clienteEntityMapper.toCliente(clienteEntity);
        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, CADASTRAR_CLIENTE_METHOD_NAME, FIM), clienteEntity);
        return clienteSaved;
    }

    @Override
    public Optional<Cliente> procurarClientePorCpf(String cpf) {
        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, PROCURAR_CLIENTE_POR_CPF_METHOD_NAME, INICIO), cpf);

        var clienteEntity = clienteRepository.findById(cpf);

        log.info(String.format(STRING_LOG_FORMAT, SERVICE_NAME, PROCURAR_CLIENTE_POR_CPF_METHOD_NAME, FIM), clienteEntity);
        return clienteEntity.map(clienteEntityMapper::toCliente);
    }
}
