package pos.fiap.lanchonete.port;

import pos.fiap.lanchonete.domain.model.DadosCliente;

import java.util.Optional;

public interface ClienteUseCasePort {

    DadosCliente cadastrar(DadosCliente dadosCliente);

    Optional<DadosCliente> procurarPorCpf(String cpf);
}
