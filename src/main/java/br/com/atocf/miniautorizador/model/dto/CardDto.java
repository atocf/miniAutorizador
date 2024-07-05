package br.com.atocf.miniautorizador.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardDto {

    @NotBlank(message = "O número do cartão não pode ser vazio")
    @Pattern(regexp = "^[0-9]{16}$", message = "O número do cartão deve conter exatamente 16 dígitos numéricos")
    private String numeroCartao;

    @NotBlank(message = "A senha não pode ser vazia")
    @Pattern(regexp = "^[0-9]{4}$", message = "A senha deve conter exatamente 4 dígitos numéricos")
    private String senha;
}