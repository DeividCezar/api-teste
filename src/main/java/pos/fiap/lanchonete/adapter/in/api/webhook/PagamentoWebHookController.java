package pos.fiap.lanchonete.adapter.in.api.webhook;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.fiap.lanchonete.adapter.in.api.webhook.dto.WebHookRequestDto;
import pos.fiap.lanchonete.port.PagamentoWebHookPort;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Pagamento Webhook", description = "Webhook de Pagamento")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class PagamentoWebHookController {

    private final PagamentoWebHookPort pagamentoWebHookPort;

    @Operation(summary = "Processar pagamento",
            description = "Processa o pagamento de acordo com o número do pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @PostMapping
    public ResponseEntity<Void> processarPagamento(@RequestBody WebHookRequestDto webHookRequestDto) {
        log.info("Processando WebHook: {}", webHookRequestDto);
        var merchantOrderId = StringUtils.substringAfter(webHookRequestDto.getResource(), "merchant_orders/");

        if (merchantOrderId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pagamentoWebHookPort.processarPagamento(merchantOrderId);
        log.info("WebHook processado");
        return ResponseEntity.ok().build();
    }

}
