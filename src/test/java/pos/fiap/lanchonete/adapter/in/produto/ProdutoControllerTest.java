package pos.fiap.lanchonete.adapter.in.produto;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;
import pos.fiap.lanchonete.adapter.in.api.produto.ProdutoController;
import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.produto.mapper.ProdutoDtoMapper;
import pos.fiap.lanchonete.domain.model.DadosProduto;
import pos.fiap.lanchonete.port.ProdutoUseCasePort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static pos.fiap.lanchonete.objectmother.dtos.in.produto.ProdutoRequestDtoObjectMother.getProdutoRequestDtoMock;
import static pos.fiap.lanchonete.objectmother.dtos.in.produto.ProdutoResponseDtoObjectMother.getProdutoResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosProdutoObjectMother.getDadosProdutoMock;
import static pos.fiap.lanchonete.objectmother.model.ProdutoObjectMother.getProdutoMock;
import static pos.fiap.lanchonete.utils.JsonStringUtils.asJsonString;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {
    private static final String ENDPOINT_PEDIDO = "/api/v1/produto";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoDtoMapper produtoDtoMapper;

    @MockBean
    private ProdutoUseCasePort produtoUseCasePort;

    @Test
    @SneakyThrows
    void testRealizarCheckout_Success() {
        final var produtoRequestDto = getProdutoRequestDtoMock();
        final var dadosProduto = getDadosProdutoMock();
        final var produtoResponseDto = getProdutoResponseDtoMock();

        when(produtoDtoMapper.toDadosProdutoFromRequestDto(any(ProdutoRequestDto.class))).thenReturn(dadosProduto);
        when(produtoUseCasePort.cadastrar(any(DadosProduto.class))).thenReturn(dadosProduto);
        when(produtoDtoMapper.toProdutoResponseDtoFromDadosProduto(any(DadosProduto.class))).thenReturn(produtoResponseDto);

        var result = mockMvc.perform(post(ENDPOINT_PEDIDO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(produtoUseCasePort, times(1)).cadastrar(any(DadosProduto.class));
        assertEquals(HttpStatus.CREATED.value(), responseMvc.getStatus());
        assertEquals(asJsonString(produtoResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testAlterarProduto_Success() {
        final var produtoRequestDto = getProdutoRequestDtoMock();
        final var dadosProduto = getDadosProdutoMock();
        final var produtoResponseDto = getProdutoResponseDtoMock();

        when(produtoDtoMapper.toDadosProdutoFromRequestDto(any(ProdutoRequestDto.class))).thenReturn(dadosProduto);
        when(produtoUseCasePort.alterar(anyString(), any(DadosProduto.class))).thenReturn(dadosProduto);
        when(produtoDtoMapper.toProdutoResponseDtoFromDadosProduto(any(DadosProduto.class))).thenReturn(produtoResponseDto);

        var result = mockMvc.perform(put(ENDPOINT_PEDIDO.concat("/123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(produtoRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(produtoUseCasePort, times(1)).alterar(anyString(), any(DadosProduto.class));
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(produtoResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testRemoverProduto_Success() {
        final var produto = getProdutoMock();

        when(produtoUseCasePort.remover(anyString())).thenReturn(Optional.of(produto));

        var result = mockMvc.perform(delete(ENDPOINT_PEDIDO.concat("/123"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(produtoUseCasePort, times(1)).remover(anyString());
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
    }

    @Test
    @SneakyThrows
    void testRemoverProduto_NotFound() {

        when(produtoUseCasePort.remover(anyString())).thenReturn(Optional.empty());

        var result = mockMvc.perform(delete(ENDPOINT_PEDIDO.concat("/123"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(produtoUseCasePort, times(1)).remover(anyString());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseMvc.getStatus());
    }

    @Test
    @SneakyThrows
    void testObterProdutosPorCategoria_Success() {
        final var dadosProdutos = List.of(getDadosProdutoMock());
        final var produtosResponseDto = List.of(getProdutoResponseDtoMock());

        when(produtoUseCasePort.buscarPorCategoria(any(CategoriaEnum.class))).thenReturn(dadosProdutos);
        when(produtoDtoMapper.toListProdutoResponseDtoFromListDadosProduto(anyList())).thenReturn(produtosResponseDto);

        var result = mockMvc.perform(get(ENDPOINT_PEDIDO.concat("/acompanhamento"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(produtoUseCasePort, times(1)).buscarPorCategoria(any(CategoriaEnum.class));
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(produtosResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testObterProdutosPorCategoria_CategoriaNotFound() {
        var result = mockMvc.perform(get(ENDPOINT_PEDIDO.concat("/acompanhamentos"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(produtoUseCasePort, times(0)).buscarPorCategoria(any(CategoriaEnum.class));
        assertEquals(HttpStatus.NOT_FOUND.value(), responseMvc.getStatus());
    }
}
