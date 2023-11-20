package br.com.api.estudamais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.estudamais.dto.LikeRequest;
import br.com.api.estudamais.model.PostLike;
import br.com.api.estudamais.service.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping
    public ResponseEntity<List<PostLike>> obterLikesPorPostId(@PathVariable Long postId) {
        List<PostLike> likes = likeService.obterLikesPorPostId(postId);
        return ResponseEntity.ok(likes);
    }

    @PostMapping
    public ResponseEntity<String> adicionarLike(@RequestBody LikeRequest likeRequest) {
        likeService.adicionarLike(likeRequest.getPostId(), likeRequest.getUserId());
        return ResponseEntity.ok("Like adicionado com sucesso");
    }
}

