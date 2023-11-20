package br.com.api.estudamais.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.Follow;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.FollowRepository;
import br.com.api.estudamais.repository.UserRepository;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersFollowedByUser(Long userId) {
        // Encontre todas as entradas na tabela Follow onde o 'followerId' é o userId fornecido
        List<Follow> follows = followRepository.findByFollowerId(userId);

        // Extrair os IDs dos usuários sendo seguidos
        List<Long> followingUserIds = follows.stream()
                                    .map(follow -> follow.getFollowing().getId()) // Supondo um método getFollowing() em Follow que retorna um User
                                    .collect(Collectors.toList());

        // Use o UserRepository para buscar os usuários correspondentes aos IDs
        return userRepository.findAllById(followingUserIds);
    }
}
