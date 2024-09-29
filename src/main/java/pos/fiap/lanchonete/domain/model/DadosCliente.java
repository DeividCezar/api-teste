package pos.fiap.lanchonete.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class DadosCliente implements Serializable {
    @Serial
    private static final long serialVersionUID = -3184562719911069742L;
    private String cpf;
    private String nome;
    private String email;
}
