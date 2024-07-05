package br.com.atocf.miniautorizador.controller;

import br.com.atocf.miniautorizador.model.dto.CardDto;
import br.com.atocf.miniautorizador.model.entity.Card;
import br.com.atocf.miniautorizador.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCard_Success() {
        CardDto cardDto = new CardDto();
        cardDto.setNumeroCartao("1234567890123456");
        cardDto.setSenha("1234");

        Card card = new Card(cardDto.getNumeroCartao(), cardDto.getSenha());
        when(cardService.createCard(cardDto.getNumeroCartao(), cardDto.getSenha())).thenReturn(card);

        ResponseEntity<?> response = cardController.createCard(cardDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Card);

        verify(cardService).createCard(cardDto.getNumeroCartao(), cardDto.getSenha());
    }

    @Test
    void createCard_CardAlreadyExists() {
        CardDto cardDto = new CardDto();
        cardDto.setNumeroCartao("1234567890123456");
        cardDto.setSenha("1234");

        when(cardService.createCard(cardDto.getNumeroCartao(), cardDto.getSenha())).thenThrow(new IllegalArgumentException("Cartão já existe"));

        ResponseEntity<?> response = cardController.createCard(cardDto);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Cartão já existe", response.getBody());

        verify(cardService).createCard(cardDto.getNumeroCartao(), cardDto.getSenha());
    }

    @Test
    void getSaldo_Success() {
        String numeroCartao = "1234567890123456";
        BigDecimal saldo = BigDecimal.valueOf(500.00);

        when(cardService.getSaldo(numeroCartao)).thenReturn(saldo);

        ResponseEntity<BigDecimal> response = cardController.getSaldo(numeroCartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());

        verify(cardService).getSaldo(numeroCartao);
    }

    @Test
    void getSaldo_CardNotFound() {
        String numeroCartao = "1234567890123456";

        when(cardService.getSaldo(numeroCartao)).thenThrow(new IllegalArgumentException("Cartão não existe"));

        ResponseEntity<BigDecimal> response = cardController.getSaldo(numeroCartao);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(cardService).getSaldo(numeroCartao);
    }
}