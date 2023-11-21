package br.com.api.estudamais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.estudamais.model.SubmittedPost;
import br.com.api.estudamais.service.SubmittedPostService;

@RestController
@RequestMapping("/api/communities/{communityId}/submitted")
public class SubmittedPostController {

    @Autowired
    private SubmittedPostService submittedPostService;

    @PostMapping
    public ResponseEntity<SubmittedPost> submitPostToCommunity(@PathVariable Long communityId,
            @RequestBody SubmittedPost submittedPost) {
        SubmittedPost savedPost = submittedPostService.save(submittedPost, communityId);
        if (savedPost != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<SubmittedPost>> getPendingPostsByCommunityId(@PathVariable Long communityId) {
        List<SubmittedPost> pendingPosts = submittedPostService.getAllPendingPostsByCommunityId(communityId);
        if (pendingPosts != null) {
            return ResponseEntity.ok(pendingPosts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{postId}/approve")
    public ResponseEntity<String> approvePost(
            @PathVariable Long communityId,
            @PathVariable Long postId) {
        boolean approved = submittedPostService.approvePost(postId, communityId);
        if (approved) {
            return ResponseEntity.ok("Postagem aprovada com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Falha ao aprovar a postagem.");
        }
    }

    @PostMapping("/{postId}/reject")
    public ResponseEntity<String> rejectPost(
            @PathVariable Long communityId,
            @PathVariable Long postId) {
        boolean rejected = submittedPostService.rejectPost(postId);
        if (rejected) {
            return ResponseEntity.ok("Postagem rejeitada com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Falha ao rejeitar a postagem.");
        }
    }
}
