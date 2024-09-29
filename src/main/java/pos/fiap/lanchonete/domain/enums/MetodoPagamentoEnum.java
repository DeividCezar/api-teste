package pos.fiap.lanchonete.domain.enums;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public enum MetodoPagamentoEnum {
    PIX("pix"),
    DEBIT_CARD("debit_card"),
    CREDIT_CARD("credit_card");

    private final String label;

    public static Optional<CategoriaEnum> getCategoriaByLabel(String label) {
        try {
            return Optional.of(CategoriaEnum.valueOf(label.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
