package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.model.DadosProduto;
import pos.fiap.lanchonete.domain.model.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoUseCasePort {

    DadosProduto cadastrar(DadosProduto dadosProduto);

    DadosProduto alterar(String id, DadosProduto dadosProduto);

    Optional<Produto> remover(String id);

    List<DadosProduto> buscarPorCategoria(CategoriaEnum categoria);
}
