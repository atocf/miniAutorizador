package br.com.atocf.miniautorizador.controller;

import br.com.atocf.miniautorizador.model.dto.TransactionDto;
import br.com.atocf.miniautorizador.model.entity.Transaction;
import br.com.atocf.miniautorizador.service.CardService;
import br.com.atocf.miniautorizador.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/transacoes")
public class TransactionController {

    @Autowired
    private CardService cardService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> processTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        try {
            cardService.processTransaction(transactionDto.getNumeroCartao(), transactionDto.getSenhaCartao(), transactionDto.getValor());
            transactionService.saveTransaction(new Transaction(transactionDto.getNumeroCartao(), transactionDto.getSenhaCartao(), transactionDto.getValor()));
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}