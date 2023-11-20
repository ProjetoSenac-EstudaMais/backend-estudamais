package br.com.api.estudamais.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.Comentario;
import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public List<Comentario> obterComentariosPorPostId(Long postId) {
        return comentarioRepository.findByPostId(postId);
    }

    public Comentario criarComentario(Long postId, Long userId, Comentario comentario) {
        Post post = postService.obterPostPorId(postId);

        if (post == null) {
            return null;
        }

        User autor = userService.obterUsuarioPorId(userId).orElse(null);

        if (autor == null) {
            return null;
        }

        comentario.setPost(post);
        comentario.setAutor(autor);

        return comentarioRepository.save(comentario);
    }
}