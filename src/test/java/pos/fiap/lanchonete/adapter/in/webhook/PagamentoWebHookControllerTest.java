package pos.fiap.lanchonete.adapter.in.webhook;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import pos.fiap.lanchonete.adapter.in.api.webhook.PagamentoWebHookController;
import pos.fiap.lanchonete.port.PagamentoWebHookPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static pos.fiap.lanchonete.objectmother.dtos.in.webhook.WebHookRequestDtoObjectMother.getWebHookRequestDtoMock;
import static pos.fiap.lanchonete.objectmother.dtos.in.webhook.WebHookRequestDtoObjectMother.getWebHookRequestDtoSemDadosMock;
import static pos.fiap.lanchonete.utils.JsonStringUtils.asJsonString;

@WebMvcTest(PagamentoWebHookController.class)
class PagamentoWebHookControllerTest {
    private static final String ENDPOINT_WEBHOOK_PAGAMENTO = "/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagamentoWebHookPort pagamentoWebHookPort;

    @Test
    @SneakyThrows
    void testProcessarPagamento_Success() {
        final var webHookRequestDto = getWebHookRequestDtoMock();

        doNothing().when(pagamentoWebHookPort).processarPagamento(anyString());

        var result = mockMvc.perform(post(ENDPOINT_WEBHOOK_PAGAMENTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(webHookRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
    }

    @Test
    @SneakyThrows
    void testProcessarPagamento_NoContent() {
        final var webHookRequestDto = getWebHookRequestDtoSemDadosMock();

        var result = mockMvc.perform(post(ENDPOINT_WEBHOOK_PAGAMENTO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(webHookRequestDto)))
                .andReturn();

        var responseMvc = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), responseMvc.getStatus());
    }
}
