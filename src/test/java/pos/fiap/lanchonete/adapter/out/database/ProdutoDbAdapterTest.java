package pos.fiap.lanchonete.adapter.out.database;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.fiap.lanchonete.adapter.out.database.entities.mapper.ClienteEntityMapper;
import pos.fiap.lanchonete.adapter.out.database.repository.ClienteRepository;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProdutoDbAdapterTest {
    @Mock
    private ClienteRepository clienteRepository;
    @Spy
    private ClienteEntityMapper clienteEntityMapper = Mappers.getMapper(ClienteEntityMapper.class);
    @InjectMocks
    private ProdutoDbAdapter mongoAdapter;


}
