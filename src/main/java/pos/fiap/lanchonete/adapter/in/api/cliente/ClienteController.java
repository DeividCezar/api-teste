package pos.fiap.lanchonete.adapter.in.api.cliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteRequestDto;
import pos.fiap.lanchonete.adapter.in.api.cliente.dto.ClienteResponseDto;
import pos.fiap.lanchonete.adapter.in.api.cliente.mapper.ClienteDtoMapper;
import pos.fiap.lanchonete.port.ClienteUseCasePort;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Cliente", description = "APIs referente ao cliente")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    private final ClienteDtoMapper clienteDtoMapper;
    private final ClienteUseCasePort clienteUseCasePort;

    @Operation(summary = "Cadastrar um cliente",
            description = "Cadastra um cliente informando o nome, cpf e email.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = ClienteResponseDto.class), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrar(@RequestBody @Valid ClienteRequestDto clienteRequestDto) {
        log.info("Cliente request: {}", clienteRequestDto);
        var dadosCliente = clienteDtoMapper.toDadosClientefromRequestDto(clienteRequestDto);

        final var clienteCadastrado = clienteUseCasePort.cadastrar(dadosCliente);

        var clienteResponse = clienteDtoMapper.toClienteResponseDtoFromDadosCliente(clienteCadastrado);
        log.info("Cliente response: {}", clienteResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @Operation(summary = "Consultar um cliente pelo CPF",
            description = "Consulta um cliente pelo CPF. Caso exista o cliente, o response contém o nome, cpf" +
                    " e email do cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ClienteResponseDto.class),
                            mediaType = APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(OK)
    @GetMapping(value = "/{cpf}")
    public ResponseEntity<ClienteResponseDto> procurarPorCpf(@PathVariable String cpf) {
        log.info("CPF request: {}", cpf);
        final var clienteCadastrado = clienteUseCasePort.procurarPorCpf(cpf);

        if (clienteCadastrado.isPresent()) {
            var clienteResponse = clienteDtoMapper.toClienteResponseDtoFromDadosCliente(clienteCadastrado.get());
            log.info("Cliente response: {}", clienteResponse);
            return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);

        } else {
            log.info("Cliente not found: {}", cpf);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
