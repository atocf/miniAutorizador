package br.com.atocf.miniautorizador.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionDto {

    @NotBlank(message = "O número do cartão não pode ser vazio")
    @Pattern(regexp = "^[0-9]{16}$", message = "O número do cartão deve conter exatamente 16 dígitos numéricos")
    private String numeroCartao;

    @NotBlank(message = "A senha do cartão não pode ser vazia")
    @Pattern(regexp = "^[0-9]{4}$", message = "A senha do cartão deve conter exatamente 4 dígitos numéricos")
    private String senhaCartao;

    @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser igual ou maior que 0.01")
    @Positive(message = "O valor deve ser positivo")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;
}
