package br.com.atocf.miniautorizador.controller;

import br.com.atocf.miniautorizador.model.dto.CardDto;
import br.com.atocf.miniautorizador.model.entity.Card;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.atocf.miniautorizador.service.CardService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody @Valid CardDto cardDto) {
        try {
            Card card = cardService.createCard(cardDto.getNumeroCartao(), cardDto.getSenha());
            return new ResponseEntity<>(card, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String numeroCartao) {
        try {
            BigDecimal saldo = cardService.getSaldo(numeroCartao);
            return new ResponseEntity<>(saldo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}