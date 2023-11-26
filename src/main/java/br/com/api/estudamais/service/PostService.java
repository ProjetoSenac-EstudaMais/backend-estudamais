package br.com.api.estudamais.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.dto.ComentarioDTO;
import br.com.api.estudamais.dto.UsuarioDTO;
import br.com.api.estudamais.model.Comentario;
import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.ComentarioRepository;
import br.com.api.estudamais.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    public List<Post> obterTodasPostagens() {
        return postRepository.findAll();
    }

    public Post criarPost(Post post) {
        return postRepository.save(post);
    }

    public Post atualizarPost(Long postId, Post novoPost) {
        Post postExistente = obterPostPorId(postId);

        if (postExistente != null) {
            postExistente.setConteudo(novoPost.getConteudo());

            return postRepository.save(postExistente);
        }

        return null;
    }

     // Método para adicionar like a um post por um usuário
     public void adicionarLike(Long postId, Long userId) {
        likeService.adicionarLike(postId, userId);
    }

    // Método para remover o like de um post por um usuário
    public void removerLike(Long postId, Long userId) {
        likeService.removerLike(postId, userId);
    }

    public void excluirPost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> obterPostagensPorUsuario(Long userId) {
        return postRepository.findByAutorId(userId);
    }

    public Post obterPostPorId(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public ComentarioDTO criarComentario(Long postId, Long userId, Comentario comentario) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Optional<User> autorOptional = userService.obterUsuarioPorId(userId);

        if (postOptional.isPresent() && autorOptional.isPresent()) {
            Post post = postOptional.get();
            User autor = autorOptional.get();

            comentario.setPost(post);
            comentario.setAutor(autor);

            Comentario comentarioSalvo = comentarioRepository.save(comentario);
            return convertToDTO(comentarioSalvo);
        }

        return null;
    }

    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setConteudo(comentario.getConteudo());
        comentarioDTO.setAutor(convertToDTO(comentario.getAutor()));
        return comentarioDTO;
    }

    private UsuarioDTO convertToDTO(User usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSobrenome(usuario.getSobrenome());
        usuarioDTO.setUsername(usuario.getUsername());
        return usuarioDTO;
    }

}
