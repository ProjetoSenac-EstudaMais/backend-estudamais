package br.com.api.estudamais.repository;

import br.com.api.estudamais.model.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByPostId(Long postId);

    @Query("SELECT pl FROM PostLike pl WHERE pl.post.id = :postId AND pl.autor.id = :autorId")
    Optional<PostLike> findLikeByPostIdAndUserId(@Param("postId") Long postId, @Param("autorId") Long autorId);

    Optional<PostLike> findByPostIdAndAutorId(Long postId, Long autorId);

}