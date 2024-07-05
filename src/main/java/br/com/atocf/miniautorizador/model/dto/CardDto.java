package br.com.atocf.miniautorizador.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDto {

    @NotBlank(message = "O número do cartão não pode ser vazio")
    private String numeroCartao;

    @NotBlank(message = "A senha não pode ser vazia")
    private String senha;
}
