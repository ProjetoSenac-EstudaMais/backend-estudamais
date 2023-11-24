package br.com.api.estudamais.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public User getUserById(Long userId) {
        Optional<User> user = usuarioRepository.findById(userId);
        return user.orElse(null);
    }

    public User getUserByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void saveUserAvatar(Long userId, MultipartFile file) throws IOException {
        User user = usuarioRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setAvatar(file.getBytes());
            usuarioRepository.save(user);
        }
    }

    public void saveUserBanner(Long userId, MultipartFile file) throws IOException {
        User user = usuarioRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBanner(file.getBytes());
            usuarioRepository.save(user);
        }
    }

    public Optional<User> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public User save(User user) {
        return usuarioRepository.save(user);
    }

    public byte[] getUserAvatar(Long userId) {
        Optional<User> userOptional = usuarioRepository.findById(userId);
        return userOptional.map(User::getAvatar).orElse(null);
    }

    public byte[] getUserBanner(Long userId) {
        Optional<User> userOptional = usuarioRepository.findById(userId);
        return userOptional.map(User::getBanner).orElse(null);
    }
}