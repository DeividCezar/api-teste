package pos.fiap.lanchonete.adapter.out.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.fiap.lanchonete.adapter.out.database.entities.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {

}
