package pos.fiap.lanchonete.domain.model.entity.mapper;

import org.mapstruct.Mapper;
import pos.fiap.lanchonete.domain.model.DadosProduto;
import pos.fiap.lanchonete.domain.model.entity.Produto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto fromDadosProduto(DadosProduto dadosProduto);

    DadosProduto toDadosProduto(Produto produto);

    List<DadosProduto> toListDadosProduto(List<Produto> produtoList);
}
