package pos.fiap.lanchonete.adapter.in.api.pagamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pos.fiap.lanchonete.domain.enums.MetodoPagamentoEnum;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagamentoRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 142698616682300465L;
    private String numeroPedido;
    private MetodoPagamentoEnum metodoPagamento;
}
