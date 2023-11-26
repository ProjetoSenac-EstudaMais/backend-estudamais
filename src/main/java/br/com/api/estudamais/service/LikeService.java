package br.com.api.estudamais.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.model.PostLike;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.PostLikeRepository;
import br.com.api.estudamais.repository.PostRepository;
import br.com.api.estudamais.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PostLike> obterLikesPorPostId(Long postId) {
        return postLikeRepository.findByPostId(postId);
    }

    public boolean checkIfUserAlreadyLiked(Long postId, Long userId) {
        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndAutorId(postId, userId);
        return existingLike.isPresent();
    }

    public void adicionarLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        // Verificar se o usuário já deu like nesta postagem
        Optional<PostLike> existingLike = postLikeRepository.findLikeByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            throw new IllegalStateException("User has already liked this post");
        }

        PostLike postLike = new PostLike();
        postLike.setPost(post);

        // Supondo que `userId` seja o ID do usuário que está dando o like
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        postLike.setAutor(user);

        postLikeRepository.save(postLike);
    }

    public void removerLike(Long postId, Long userId) {
        Optional<PostLike> existingLike = postLikeRepository.findLikeByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            postLikeRepository.delete(existingLike.get());
        } else {
            throw new EntityNotFoundException("Like not found");
        }
    }

}