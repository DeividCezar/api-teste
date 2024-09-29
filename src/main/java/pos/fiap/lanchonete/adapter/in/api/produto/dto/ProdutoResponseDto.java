package pos.fiap.lanchonete.adapter.in.api.produto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;

@Getter
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -4825697705033933620L;
    private String id;
    private String nome;
    private CategoriaEnum categoria;
    private Double preco;
    private String descricao;
    private URL imagem;
}
