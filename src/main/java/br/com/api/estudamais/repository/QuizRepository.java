package br.com.api.estudamais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.api.estudamais.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
