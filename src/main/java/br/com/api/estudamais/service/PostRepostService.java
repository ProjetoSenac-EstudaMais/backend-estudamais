package br.com.api.estudamais.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Optional<Post> originalPost = postRepository.findById(postId);
    
        if (user.isPresent() && originalPost.isPresent()) {
            // Cria um novo PostRepost
            PostRepost postRepost = new PostRepost();
            
            // Define o usuário e o post original
            postRepost.setUser(user.get());
            postRepost.setPost(originalPost.get());
            postRepost.setIsRepost("repost");
            
            // Salva o PostRepost
            postRepostRepository.save(postRepost);
            
            return true;
        } else {
            return false;
        }
    }
    

    public List<PostRepost> obterTodosReposts() {
        return postRepostRepository.findAll();
    }

     public List<Map<String, Long>> obterTodosRepostInfo() {
        List<Object[]> repostInfo = postRepostRepository.findAllRepostInfo();

        List<Map<String, Long>> resultList = new ArrayList<>();
        for (Object[] info : repostInfo) {
            Map<String, Long> data = new HashMap<>();
            data.put("postId", (Long) info[0]);
            data.put("userId", (Long) info[1]);
            resultList.add(data);
        }

        return resultList;
    }

    public int obterNumeroReposts(Long postId) {
        return postRepostRepository.countByPostId(postId);
    }
}
