package br.com.api.estudamais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.estudamais.model.PostLike;
import br.com.api.estudamais.service.LikeService;
import br.com.api.estudamais.service.PostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<PostLike>> obterLikesPorPostId(@PathVariable Long postId) {
        List<PostLike> likes = likeService.obterLikesPorPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/checkIfUserAlreadyLiked/{postId}/{userId}")
    public ResponseEntity<Boolean> checkIfUserAlreadyLiked(@PathVariable Long postId, @PathVariable Long userId) {
        boolean alreadyLiked = likeService.checkIfUserAlreadyLiked(postId, userId);
        return ResponseEntity.ok(alreadyLiked);
    }

    // Endpoint para adicionar um like a um post por um usuário
    @PostMapping("/addLike/{postId}/{userId}")
    public ResponseEntity<String> adicionarLike(@PathVariable Long postId, @PathVariable Long userId) {
        postService.adicionarLike(postId, userId);
        return ResponseEntity.ok("Like adicionado com sucesso");
    }

    // Endpoint para remover um like de um post por um usuário
    @PostMapping("/removeLike/{postId}/{userId}")
    public ResponseEntity<String> removerLike(@PathVariable Long postId, @PathVariable Long userId) {
        postService.removerLike(postId, userId);
        return ResponseEntity.ok("Like removido com sucesso");
    }

}
