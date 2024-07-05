package br.com.atocf.miniautorizador.service;

import br.com.atocf.miniautorizador.model.entity.Transaction;
import br.com.atocf.miniautorizador.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}