package br.com.api.estudamais.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.estudamais.model.Follow;
import br.com.api.estudamais.model.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // Encontra um registro de Follow pelo ID do seguidor e do usuário seguido
    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);    
    // Encontra todos os registros de Follow pelo ID do seguidor
    List<Follow> findByFollowerId(Long followerId);
    // Encontra todos os registros de Follow pelo ID do usuário seguido
    List<Follow> findByFollowingId(Long followingId);

    List<Follow> findByFollowerAndFollowing(User follower, User following);
}

