package br.com.api.estudamais.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.model.PostLike;
import br.com.api.estudamais.repository.PostLikeRepository;
import br.com.api.estudamais.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    public List<PostLike> obterLikesPorPostId(Long postId) {
        return postLikeRepository.findByPostId(postId);
    }

    public void adicionarLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        // Verificar se o usuário já deu like nesta postagem
        if (postLikeRepository.findByPostIdAndAutorId(postId, userId).isPresent()) {
            throw new IllegalStateException("User has already liked this post");
        }

        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setAutor(post.getAutor());
        postLikeRepository.save(postLike);
    }
}