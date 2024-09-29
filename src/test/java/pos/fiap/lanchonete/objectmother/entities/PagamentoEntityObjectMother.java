package pos.fiap.lanchonete.objectmother.entities;

import pos.fiap.lanchonete.adapter.out.database.entities.PagamentoEntity;

import static pos.fiap.lanchonete.domain.enums.StatusPagamentoEnum.APROVADO;
import static pos.fiap.lanchonete.objectmother.entities.PedidoEntityObjectMother.getPedidoEntityMock;

public class PagamentoEntityObjectMother {

    public static PagamentoEntity getPagamentoAprovadoEntityMock() {
        return PagamentoEntity.builder()
                .statusPagamento(APROVADO)
                .pedidoEntity(getPedidoEntityMock())
                .build();
    }
}
