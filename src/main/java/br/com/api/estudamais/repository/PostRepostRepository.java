package br.com.api.estudamais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.api.estudamais.model.PostRepost;

@Repository
public interface PostRepostRepository extends JpaRepository<PostRepost, Long> {
    @Query("SELECT COUNT(pr) FROM PostRepost pr WHERE pr.post.id = :postId")
    int countByPostId(Long postId);
}