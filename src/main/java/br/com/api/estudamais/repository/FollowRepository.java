package br.com.api.estudamais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.estudamais.model.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // Encontra um registro de Follow pelo ID do seguidor e do usuário seguido
    Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId);
    
    // Encontra todos os registros de Follow pelo ID do seguidor
    List<Follow> findByFollowerId(Long followerId);
    
    // Encontra todos os registros de Follow pelo ID do usuário seguido
    List<Follow> findByFollowingId(Long followingId);
}

