package br.com.atocf.miniautorizador.service;

import br.com.atocf.miniautorizador.model.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.atocf.miniautorizador.repository.CardRepository;

import java.math.BigDecimal;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(String numeroCartao, String senha) {
        if (cardRepository.existsById(numeroCartao)) {
            throw new IllegalArgumentException("CARTAO_EXISTENTE");
        }
        return cardRepository.save(new Card(numeroCartao, senha));
    }

    public BigDecimal getSaldo(String numeroCartao) {
        Card card = cardRepository.findById(numeroCartao)
                .orElseThrow(() -> new IllegalArgumentException("CARTAO_INEXISTENTE"));
        return card.getSaldo();
    }

    public void processTransaction(String numeroCartao, String senha, BigDecimal valor) {
        Card card = cardRepository.findById(numeroCartao)
                .orElseThrow(() -> new IllegalArgumentException("CARTAO_INEXISTENTE"));

        if (!card.getSenha().equals(senha)) {
            throw new IllegalArgumentException("SENHA_INVALIDA");
        }

        if (card.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("SALDO_INSUFICIENTE");
        }

        card.setSaldo(card.getSaldo().subtract(valor));
        cardRepository.save(card);
    }
}