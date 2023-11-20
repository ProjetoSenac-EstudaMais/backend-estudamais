package br.com.api.estudamais.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.model.PostRepost;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.PostRepository;
import br.com.api.estudamais.repository.PostRepostRepository;
import br.com.api.estudamais.repository.UserRepository;

@Service
public class PostRepostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostRepostRepository postRepostRepository;

    public boolean fazerRepost(Long userId, Long postId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Post> post = postRepository.findById(postId);

        if (user.isPresent() && post.isPresent()) {
            PostRepost postRepost = new PostRepost();
            postRepost.setUser(user.get());
            postRepost.setPost(post.get());
            postRepostRepository.save(postRepost);
            return true; // Retorna true se a repostagem for bem-sucedida
        } else {
            return false; // Retorna false se o usuário ou o post não forem encontrados
        }
    }

    public int obterNumeroReposts(Long postId) {
        return postRepostRepository.countByPostId(postId);
    }
}
