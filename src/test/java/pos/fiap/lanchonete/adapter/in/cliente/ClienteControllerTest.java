package pos.fiap.lanchonete.adapter.in.cliente;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import pos.fiap.lanchonete.adapter.in.api.cliente.ClienteController;
import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteRequestDto;
import pos.fiap.lanchonete.adapter.in.api.cliente.mapper.ClienteDtoMapper;
import pos.fiap.lanchonete.domain.model.DadosCliente;
import pos.fiap.lanchonete.port.ClienteUseCasePort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static pos.fiap.lanchonete.objectmother.dtos.in.cliente.ClienteRequestDtoObjectMother.getClienteRequestDtoMock;
import static pos.fiap.lanchonete.objectmother.dtos.in.cliente.ClienteResponseDtoObjectMother.getClienteResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosClienteObjectMother.getDadosClienteMock;
import static pos.fiap.lanchonete.utils.JsonStringUtils.asJsonString;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {
    private static final String ENDPOINT_CLIENTE = "/api/v1/cliente";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteDtoMapper clienteDtoMapper;

    @MockBean
    private ClienteUseCasePort clienteUseCasePort;

    @Test
    @SneakyThrows
    void testCadastrarCliente_Success() {
        final var clienteRequestDto = getClienteRequestDtoMock();
        final var dadosCliente = getDadosClienteMock();
        final var clienteResponseDto = getClienteResponseDtoMock();

        when(clienteDtoMapper.toDadosClientefromRequestDto(any(ClienteRequestDto.class)))
                .thenReturn(dadosCliente);
        when(clienteUseCasePort.cadastrar(any(DadosCliente.class))).thenReturn(dadosCliente);
        when(clienteDtoMapper.toClienteResponseDtoFromDadosCliente(any(DadosCliente.class)))
                .thenReturn(clienteResponseDto);

        var result = mockMvc.perform(post(ENDPOINT_CLIENTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(clienteRequestDto)))
                .andReturn();

        MockHttpServletResponse responseMvc = result.getResponse();
        verify(clienteUseCasePort, times(1)).cadastrar(any(DadosCliente.class));
        assertEquals(HttpStatus.CREATED.value(), responseMvc.getStatus());
        assertEquals(asJsonString(clienteResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testProcurarClientePorCpf_Success() {
        final var dadosCliente = getDadosClienteMock();
        final var clienteResponseDto = getClienteResponseDtoMock();
        when(clienteUseCasePort.procurarPorCpf(anyString())).thenReturn(Optional.ofNullable(dadosCliente));
        when(clienteDtoMapper.toClienteResponseDtoFromDadosCliente(any(DadosCliente.class)))
                .thenReturn(clienteResponseDto);

        var result = mockMvc.perform(get(ENDPOINT_CLIENTE.concat("/1234"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(clienteUseCasePort, times(1)).procurarPorCpf(anyString());
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(clienteResponseDto), result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testProcurarClientePorCpf_NotFound() {
        when(clienteUseCasePort.procurarPorCpf(anyString())).thenReturn(Optional.empty());

        var result = mockMvc.perform(get(ENDPOINT_CLIENTE.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var responseMvc = result.getResponse();
        verify(clienteUseCasePort, times(1)).procurarPorCpf(anyString());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseMvc.getStatus());
    }

}
