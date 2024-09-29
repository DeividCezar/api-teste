package pos.fiap.lanchonete.adapter.in.api.cliente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteRequestDto implements Serializable {
    private static final String CAMPO_OBRIGATORIO = "CAMPO OBRIGATÓRIO";
    @Serial
    private static final long serialVersionUID = -3401826491154036524L;
    @NotEmpty(message = CAMPO_OBRIGATORIO)
    private String cpf;
    @NotEmpty(message = CAMPO_OBRIGATORIO)
    private String nome;
    @NotEmpty(message = CAMPO_OBRIGATORIO)
    private String email;
}
