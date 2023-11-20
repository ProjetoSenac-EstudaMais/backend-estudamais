package br.com.api.estudamais.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.Follow;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.FollowRepository;
import br.com.api.estudamais.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private FollowRepository followRepository;

    public List<User> obterTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<User> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public User criarUsuario(User usuario) {
        return usuarioRepository.save(usuario);
    }

    public User atualizarUsuario(Long id, User novoUsuario) {
        if (usuarioRepository.existsById(id)) {
            novoUsuario.setId(id);
            return usuarioRepository.save(novoUsuario);
        }
        return null;
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void seguirUsuario(Long idSeguidor, Long idSeguindo) {
        User seguidor = usuarioRepository.findById(idSeguidor).orElse(null);
        User seguindo = usuarioRepository.findById(idSeguindo).orElse(null);

        if (seguidor != null && seguindo != null) {
            Follow follow = new Follow();
            follow.setFollower(seguidor);
            follow.setFollowing(seguindo);
            followRepository.save(follow);
        }
    }

    public void deixarDeSeguirUsuario(Long idSeguidor, Long idSeguindo) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(idSeguidor, idSeguindo);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }
}