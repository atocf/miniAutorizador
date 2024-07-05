package br.com.atocf.miniautorizador.service;

import br.com.atocf.miniautorizador.model.entity.Card;
import br.com.atocf.miniautorizador.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCard_Success() {
        String numeroCartao = "1234567890123456";
        String senha = "1234";
        Card card = new Card(numeroCartao, senha);

        when(cardRepository.existsById(numeroCartao)).thenReturn(false);
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card result = cardService.createCard(numeroCartao, senha);

        assertNotNull(result);
        assertEquals(numeroCartao, result.getNumeroCartao());
        assertEquals(senha, result.getSenha());
        assertEquals(BigDecimal.valueOf(500.00), result.getSaldo());

        verify(cardRepository).existsById(numeroCartao);
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    void createCard_CardAlreadyExists() {
        String numeroCartao = "1234567890123456";
        String senha = "1234";

        when(cardRepository.existsById(numeroCartao)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> cardService.createCard(numeroCartao, senha));

        verify(cardRepository).existsById(numeroCartao);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void getSaldo_Success() {
        String numeroCartao = "1234567890123456";
        Card card = new Card(numeroCartao, "1234");
        card.setSaldo(BigDecimal.valueOf(500.00));

        when(cardRepository.findById(numeroCartao)).thenReturn(Optional.of(card));

        BigDecimal result = cardService.getSaldo(numeroCartao);

        assertEquals(BigDecimal.valueOf(500.00), result);

        verify(cardRepository).findById(numeroCartao);
    }

    @Test
    void getSaldo_CardNotFound() {
        String numeroCartao = "1234567890123456";

        when(cardRepository.findById(numeroCartao)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> cardService.getSaldo(numeroCartao));

        verify(cardRepository).findById(numeroCartao);
    }

    @Test
    void processTransaction_Success() {
        String numeroCartao = "1234567890123456";
        String senha = "1234";
        BigDecimal valor = BigDecimal.valueOf(100.00);
        Card card = new Card(numeroCartao, senha);
        card.setSaldo(BigDecimal.valueOf(500.00));

        when(cardRepository.findById(numeroCartao)).thenReturn(Optional.of(card));

        cardService.processTransaction(numeroCartao, senha, valor);

        assertEquals(BigDecimal.valueOf(400.00), card.getSaldo());

        verify(cardRepository).findById(numeroCartao);
        verify(cardRepository).save(card);
    }

    @Test
    void processTransaction_CardNotFound() {
        String numeroCartao = "1234567890123456";
        String senha = "1234";
        BigDecimal valor = BigDecimal.valueOf(100.00);

        when(cardRepository.findById(numeroCartao)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> cardService.processTransaction(numeroCartao, senha, valor));

        verify(cardRepository).findById(numeroCartao);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void processTransaction_InvalidPassword() {
        String numeroCartao = "1234567890123456";
        String senha = "1234";
        String wrongSenha = "4321";
        BigDecimal valor = BigDecimal.valueOf(100.00);
        Card card = new Card(numeroCartao, senha);

        when(cardRepository.findById(numeroCartao)).thenReturn(Optional.of(card));

        assertThrows(IllegalArgumentException.class, () -> cardService.processTransaction(numeroCartao, wrongSenha, valor));

        verify(cardRepository).findById(numeroCartao);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void processTransaction_InsufficientFunds() {
        String numeroCartao = "1234567890123456";
        String senha = "1234";
        BigDecimal valor = BigDecimal.valueOf(600.00);
        Card card = new Card(numeroCartao, senha);
        card.setSaldo(BigDecimal.valueOf(500.00));

        when(cardRepository.findById(numeroCartao)).thenReturn(Optional.of(card));

        assertThrows(IllegalArgumentException.class, () -> cardService.processTransaction(numeroCartao, senha, valor));

        verify(cardRepository).findById(numeroCartao);
        verify(cardRepository, never()).save(any(Card.class));
    }
}
