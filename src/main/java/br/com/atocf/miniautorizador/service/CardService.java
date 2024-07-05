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
            throw new IllegalArgumentException("Cartão já existe");
        }
        return cardRepository.save(new Card(numeroCartao, senha));
    }

    public BigDecimal getSaldo(String numeroCartao) {
        Card card = cardRepository.findById(numeroCartao)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não existe"));
        return card.getSaldo();
    }

    public void processTransaction(String numeroCartao, String senha, BigDecimal valor) {
        Card card = cardRepository.findById(numeroCartao)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não existe"));

        if (!card.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha inválida");
        }

        if (card.getSaldo().compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        card.setSaldo(card.getSaldo().subtract(valor));
        cardRepository.save(card);
    }
}