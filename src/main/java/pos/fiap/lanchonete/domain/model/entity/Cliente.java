package pos.fiap.lanchonete.domain.model.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class Cliente implements Serializable {
    @Serial
    private static final long serialVersionUID = 8684677609977402438L;
    private String cpf;
    private String nome;
    private String email;
}
