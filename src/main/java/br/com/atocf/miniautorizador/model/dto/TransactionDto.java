package br.com.atocf.miniautorizador.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransactionDto {

    @NotBlank(message = "O número do cartão não pode ser vazio")
    private String numeroCartao;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senhaCartao;

    @NotNull(message = "O valor é obrigátorio")
    private BigDecimal valor;
}
