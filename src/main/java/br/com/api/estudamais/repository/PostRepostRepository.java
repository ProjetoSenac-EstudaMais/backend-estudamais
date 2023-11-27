package br.com.api.estudamais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.api.estudamais.model.PostRepost;

@Repository
public interface PostRepostRepository extends JpaRepository<PostRepost, Long> {
    @Query("SELECT COUNT(pr) FROM PostRepost pr WHERE pr.post.id = :postId")
    int countByPostId(Long postId);

    @Query("SELECT pr FROM PostRepost pr WHERE pr.post.id = :postId")
    List<PostRepost> findByPostId(@Param("postId") Long postId);

    @Query("SELECT pr.post.id, pr.user.id FROM PostRepost pr WHERE pr.isRepost = 'repost'")
    List<Object[]> findAllRepostInfo();
}