package pos.fiap.lanchonete.adapter.out.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.fiap.lanchonete.adapter.out.database.entities.PagamentoEntity;
import pos.fiap.lanchonete.adapter.out.database.entities.PedidoEntity;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, String> {

    Optional<PagamentoEntity> findByPedidoEntity(PedidoEntity pedidoEntity);

}