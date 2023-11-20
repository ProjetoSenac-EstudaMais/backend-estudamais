package br.com.api.estudamais.repository;

import br.com.api.estudamais.model.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPostId(Long postId);

    Optional<PostLike> findByPostIdAndAutorId(Long postId, Long autorId);
}