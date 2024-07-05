package br.com.atocf.miniautorizador.repository;

import br.com.atocf.miniautorizador.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
}
