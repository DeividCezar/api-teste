package pos.fiap.lanchonete.adapter.in.api.produto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoResponseDto;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.domain.model.DadosProduto;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ProdutoDtoMapper {
    DadosProduto toDadosProdutoFromRequestDto(ProdutoRequestDto requestDto);

    ProdutoResponseDto toProdutoResponseDtoFromDadosProduto(DadosProduto dadosProduto);

    List<ProdutoResponseDto> toListProdutoResponseDtoFromListDadosProduto(List<DadosProduto> dadosProduto);

    @Named("toCategoriaEnum")
    default Optional<CategoriaEnum> toCategoriaEnum(String categoria) {
        return CategoriaEnum.getCategoriaByLabel(categoria);
    }
}
