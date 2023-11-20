package br.com.api.estudamais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.estudamais.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
