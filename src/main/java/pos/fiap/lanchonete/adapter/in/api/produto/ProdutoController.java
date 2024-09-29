package pos.fiap.lanchonete.adapter.in.api.produto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoResponseDto;
import pos.fiap.lanchonete.adapter.in.api.produto.mapper.ProdutoDtoMapper;
import pos.fiap.lanchonete.port.ProdutoUseCasePort;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pos.fiap.lanchonete.domain.enums.CategoriaEnum.getCategoriaByLabel;

@Tag(name = "Produto", description = "APIs referente ao Produto")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/produto")
@RestController
public class ProdutoController {

    private final ProdutoDtoMapper dtoMapper;
    private final ProdutoUseCasePort produtoUseCasePort;

    @Operation(summary = "Cadastrar um produto",
            description = "Cadastra um produto informando o nome, categoria, descricao e url da imagem.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = ProdutoResponseDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrar(@RequestBody ProdutoRequestDto produtoRequest) {
        log.info("Produto request: {}", produtoRequest);

        var dadosProduto = dtoMapper.toDadosProdutoFromRequestDto(produtoRequest);

        var produtoCadastrado = produtoUseCasePort.cadastrar(dadosProduto);

        var produtoResponse = dtoMapper.toProdutoResponseDtoFromDadosProduto(produtoCadastrado);
        log.info("Produto response: {}", produtoResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }


    @Operation(summary = "Alterar um produto",
            description = "Altera um produto informando o id e o que desejar alterar.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ProdutoResponseDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> editar(@PathVariable String id, @RequestBody ProdutoRequestDto produtoRequestDto) {
        log.info("Produto request: {}", produtoRequestDto);

        var dadosProduto = dtoMapper.toDadosProdutoFromRequestDto(produtoRequestDto);

        var produtoAlterado = produtoUseCasePort.alterar(id, dadosProduto);

        var produtoResponse = dtoMapper.toProdutoResponseDtoFromDadosProduto(produtoAlterado);

        log.info("Produto response: {}", produtoResponse);
        return ResponseEntity.status(OK).body(produtoResponse);
    }

    @Operation(summary = "Remove um produto",
            description = "Remove um produto informando o id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable String id) {
        log.info("Produto id: {}", id);

        var produto = produtoUseCasePort.remover(id);

        if (produto.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).build();
        }

        log.info("Produto Id: {} removido", id);
        return ResponseEntity.status(OK).body("Produto removido com sucesso");
    }

    @Operation(summary = "Obter produtos por categoria",
            description = "Lista todos produtos de uma determinada categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ProdutoResponseDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @GetMapping("/{categoria}")
    public ResponseEntity<List<ProdutoResponseDto>> buscar(@PathVariable String categoria) {
        log.info("Categoria request: {}", categoria);

        var categoriaEnum = getCategoriaByLabel(categoria);

        if (categoriaEnum.isEmpty()) {
            log.error("Categoria informada não existe: {}", categoria);
            return ResponseEntity.status(NOT_FOUND).build();
        }

        var produtosList = produtoUseCasePort.buscarPorCategoria(categoriaEnum.get());

        var produtoResponseList = dtoMapper.toListProdutoResponseDtoFromListDadosProduto(produtosList);

        log.info("List Produto response: {} por categoria: {}", produtoResponseList, categoria);
        return ResponseEntity.status(OK).body(produtoResponseList);
    }
}
