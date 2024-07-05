package br.com.atocf.miniautorizador.service;

import br.com.atocf.miniautorizador.model.entity.Transaction;
import br.com.atocf.miniautorizador.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTransaction_Success() {
        Transaction transaction = new Transaction("1234567890123456", "1234", BigDecimal.valueOf(100.00));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.saveTransaction(transaction);

        assertNotNull(result);
        assertEquals(transaction.getNumeroCartao(), result.getNumeroCartao());
        assertEquals(transaction.getSenhaCartao(), result.getSenhaCartao());
        assertEquals(transaction.getValor(), result.getValor());

        verify(transactionRepository).save(transaction);
    }
}