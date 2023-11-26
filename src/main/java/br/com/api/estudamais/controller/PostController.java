package br.com.api.estudamais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.service.PostRepostService;
import br.com.api.estudamais.service.PostService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepostService repostService;

    @GetMapping
    public ResponseEntity<List<Post>> obterTodasPostagens() {
        List<Post> postagens = postService.obterTodasPostagens();
        
        for (Post post : postagens) {
            Long postId = post.getId();
            int numReposts = repostService.obterNumeroReposts(postId);
            post.setNumeroReposts(numReposts);
        }
        
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Post>> obterPostagensPorUsuario(@PathVariable Long userId) {
        List<Post> postagens = postService.obterPostagensPorUsuario(userId);
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> obterPostPorId(@PathVariable Long postId) {
        Post post = postService.obterPostPorId(postId);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Post> criarPost(@RequestBody Post post) {
        Post novaPostagem = postService.criarPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPostagem);
    }

    @PostMapping("/{postId}/repostar/{userId}")
    public ResponseEntity<String> repostar(@PathVariable Long postId, @PathVariable Long userId) {
        boolean reposted = repostService.fazerRepost(userId, postId);
        if (reposted) {
            return ResponseEntity.ok("Postagem repostada com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Falha ao repostar a postagem.");
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> atualizarPost(@PathVariable Long postId, @RequestBody Post novoPost) {
        Post postAtualizado = postService.atualizarPost(postId, novoPost);
        return postAtualizado != null ? ResponseEntity.ok(postAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> excluirPost(@PathVariable Long postId) {
        postService.excluirPost(postId);
        return ResponseEntity.noContent().build();
    }
}
