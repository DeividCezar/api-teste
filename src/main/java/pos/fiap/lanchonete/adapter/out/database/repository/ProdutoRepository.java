package pos.fiap.lanchonete.adapter.out.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.fiap.lanchonete.adapter.out.database.entities.ProdutoEntity;
import pos.fiap.lanchonete.domain.enums.CategoriaEnum;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, String> {

    List<ProdutoEntity> findByCategoria(CategoriaEnum categoria);

}
