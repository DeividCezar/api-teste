package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.model.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoDbAdapterPort {

    Produto cadastrarProduto(Produto produto);

    Produto alterarProduto(String id, Produto produto);

    void removerProduto(String id);

    List<Produto> buscarProdutoPorCategoria(CategoriaEnum categoria);

    Optional<Produto> buscarPorId(String id);
}