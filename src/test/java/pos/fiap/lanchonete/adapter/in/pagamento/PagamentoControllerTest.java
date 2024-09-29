package pos.fiap.lanchonete.adapter.in.pagamento;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.fiap.lanchonete.adapter.in.api.pagamento.PagamentoController;
import pos.fiap.lanchonete.adapter.in.api.pagamento.dto.PagamentoRequestDto;
import pos.fiap.lanchonete.adapter.in.api.pagamento.mapper.PagamentoDtoMapper;
import pos.fiap.lanchonete.domain.model.DadosPagamento;
import pos.fiap.lanchonete.port.PagamentoUseCasePort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static pos.fiap.lanchonete.objectmother.dtos.in.pagamento.PagamentoRequestDtoObjectMother.getPagamentoRequestDtoMock;
import static pos.fiap.lanchonete.objectmother.dtos.in.pagamento.PagamentoResponseDtoObjectMother.getPagamentoAprovadoResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosPagamentoObjectMother.getDadosPagamentoMock;
import static pos.fiap.lanchonete.utils.JsonStringUtils.asJsonString;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {
    private static final String ENDPOINT_PAGAMENTO = "/api/v1/pagamento";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagamentoDtoMapper pagamentoDtoMapper;

    @MockBean
    private PagamentoUseCasePort pagamentoUseCasePort;

    @Test
    @SneakyThrows
    void testRealizarPagamento_Success() {
        final var pagamentoRequestDto = getPagamentoRequestDtoMock();
        final var dadosPagamento = getDadosPagamentoMock();
        final var pagamentoResponseDto = getPagamentoAprovadoResponseDtoMock();

        when(pagamentoDtoMapper.toDadosPagamentoFromPagamentoRequestDto(any(PagamentoRequestDto.class)))
                .thenReturn(dadosPagamento);
        when(pagamentoUseCasePort.processarPagamento(any(DadosPagamento.class))).thenReturn(dadosPagamento);
        when(pagamentoDtoMapper.toPagamentoResponseDtoFromDadosPagamento(any(DadosPagamento.class)))
                .thenReturn(pagamentoResponseDto);

        var result = mockMvc.perform(post(ENDPOINT_PAGAMENTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pagamentoRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(pagamentoUseCasePort, times(1)).processarPagamento(any(DadosPagamento.class));
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(pagamentoResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testBuscarDadosPagamento_Success() {
        final var dadosPagamento = getDadosPagamentoMock();
        final var pagamentoResponseDto = getPagamentoAprovadoResponseDtoMock();

        when(pagamentoUseCasePort.obterDadosPagamento(anyString())).thenReturn(dadosPagamento);
        when(pagamentoDtoMapper.toPagamentoResponseDtoFromDadosPagamento(any(DadosPagamento.class)))
                .thenReturn(pagamentoResponseDto);

        var result = mockMvc.perform(get(ENDPOINT_PAGAMENTO.concat("/123"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(pagamentoUseCasePort, times(1)).obterDadosPagamento(anyString());
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(pagamentoResponseDto), result.getResponse().getContentAsString());
    }

}
