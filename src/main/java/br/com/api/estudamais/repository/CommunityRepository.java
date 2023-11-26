package br.com.api.estudamais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.api.estudamais.model.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByNomeContainingIgnoreCase(String nome);
}

