package br.com.api.estudamais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.api.estudamais.model.ApprovedPost;
import java.util.List;

@Repository
public interface ApprovedPostRepository extends JpaRepository<ApprovedPost, Long> {
    List<ApprovedPost> findByCommunityId(Long communityId);
}
