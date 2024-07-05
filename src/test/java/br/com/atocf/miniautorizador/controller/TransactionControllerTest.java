package br.com.atocf.miniautorizador.controller;

import br.com.atocf.miniautorizador.model.dto.TransactionDto;
import br.com.atocf.miniautorizador.model.entity.Transaction;
import br.com.atocf.miniautorizador.service.CardService;
import br.com.atocf.miniautorizador.service.TransactionService;
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

class TransactionControllerTest {

    @Mock
    private CardService cardService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processTransaction_Success() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setNumeroCartao("1234567890123456");
        transactionDto.setSenhaCartao("1234");
        transactionDto.setValor(BigDecimal.valueOf(100.00));

        doNothing().when(cardService).processTransaction(transactionDto.getNumeroCartao(), transactionDto.getSenhaCartao(), transactionDto.getValor());
        when(transactionService.saveTransaction(any(Transaction.class))).thenReturn(new Transaction());

        ResponseEntity<String> response = transactionController.processTransaction(transactionDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("OK", response.getBody());

        verify(cardService).processTransaction(transactionDto.getNumeroCartao(), transactionDto.getSenhaCartao(), transactionDto.getValor());
        verify(transactionService).saveTransaction(any(Transaction.class));
    }

    @Test
    void processTransaction_Failure() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setNumeroCartao("1234567890123456");
        transactionDto.setSenhaCartao("1234");
        transactionDto.setValor(BigDecimal.valueOf(100.00));

        doThrow(new IllegalArgumentException("Saldo insuficiente")).when(cardService).processTransaction(transactionDto.getNumeroCartao(), transactionDto.getSenhaCartao(), transactionDto.getValor());

        ResponseEntity<String> response = transactionController.processTransaction(transactionDto);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Saldo insuficiente", response.getBody());

        verify(cardService).processTransaction(transactionDto.getNumeroCartao(), transactionDto.getSenhaCartao(), transactionDto.getValor());
        verify(transactionService, never()).saveTransaction(any(Transaction.class));
    }
}