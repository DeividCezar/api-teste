package pos.fiap.lanchonete.adapter.in.api.pedido.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pos.fiap.lanchonete.adapter.in.api.produto.dto.ProdutoRequestDto;
import pos.fiap.lanchonete.domain.enums.StatusPedidoEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -2674614054267148943L;
    private String cpfCliente;
    private StatusPedidoEnum statusPedido;
    private List<ProdutoRequestDto> itens;
}
