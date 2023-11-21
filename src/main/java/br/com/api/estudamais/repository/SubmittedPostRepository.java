package br.com.api.estudamais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.estudamais.model.SubmittedPost;

@Repository
public interface SubmittedPostRepository extends JpaRepository<SubmittedPost, Long> {
    List<SubmittedPost> findByCommunityIdAndApprovedFalse(Long communityId);
}

