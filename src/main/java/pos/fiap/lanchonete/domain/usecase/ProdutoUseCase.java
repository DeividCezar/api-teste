package pos.fiap.lanchonete.domain.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.model.DadosProduto;
import pos.fiap.lanchonete.domain.model.entity.Produto;
import pos.fiap.lanchonete.domain.model.entity.mapper.ProdutoMapper;
import pos.fiap.lanchonete.port.ProdutoDbAdapterPort;
import pos.fiap.lanchonete.port.ProdutoUseCasePort;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoUseCase implements ProdutoUseCasePort {

    private final ProdutoMapper produtoMapper;
    private final ProdutoDbAdapterPort produtoDbAdapterPort;

    @Override
    public DadosProduto cadastrar(DadosProduto dadosProduto) {
        var produto = produtoMapper.fromDadosProduto(dadosProduto);
        produto = produtoDbAdapterPort.cadastrarProduto(produto);
        return produtoMapper.toDadosProduto(produto);
    }

    @Override
    public DadosProduto alterar(String id, DadosProduto dadosProduto) {
        var produto = produtoMapper.fromDadosProduto(dadosProduto);
        produto = produtoDbAdapterPort.alterarProduto(id, produto);
        return produtoMapper.toDadosProduto(produto);
    }

    @Override
    public Optional<Produto> remover(String id) {
        var produto = produtoDbAdapterPort.buscarPorId(id);

        if (produto.isEmpty()) {
            return Optional.empty();
        }

        produtoDbAdapterPort.removerProduto(id);

        return produto;
    }

    @Override
    public List<DadosProduto> buscarPorCategoria(CategoriaEnum categoria) {
        var produtoList = produtoDbAdapterPort.buscarProdutoPorCategoria(categoria);
        return produtoMapper.toListDadosProduto(produtoList);
    }
}
