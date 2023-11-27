package br.com.api.estudamais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.estudamais.dto.PostDTO;
import br.com.api.estudamais.model.Post;
import br.com.api.estudamais.model.PostRepost;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.service.PostRepostService;
import br.com.api.estudamais.service.PostService;
import br.com.api.estudamais.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepostService repostService;

    @Autowired
    private UserService userService;

    private long proximoId = 1;

    @GetMapping
    public ResponseEntity<List<PostDTO>> obterTodasPostagens() {
        List<Post> postagensOriginais = postService.obterTodasPostagens();
        List<PostRepost> reposts = repostService.obterTodosReposts();

        List<PostDTO> todasPostagens = new ArrayList<>(); // Inicialize a lista

        for (Post post : postagensOriginais) {
            PostDTO postDTO = new PostDTO();
            postDTO.setId(proximoId++);
            postDTO.setConteudo(post.getConteudo());
            postDTO.setUserId(post.getUserId());
            postDTO.setRepost(false); // Indica que é uma postagem original
            postDTO.setPostId(post.getId()); // Definir postId com base no Post original

            // Obter detalhes do usuário e definir no DTO
            User user = userService.getUserById(post.getUserId());
            if (user != null) {
                postDTO.setNome(user.getNome());
                postDTO.setSobrenome(user.getSobrenome());
                postDTO.setUsername(user.getUsername());
                postDTO.setAvatar(user.getAvatar());
            }

            todasPostagens.add(postDTO);
        }

        for (PostRepost repost : reposts) {
            PostDTO repostDTO = new PostDTO();
            repostDTO.setId(proximoId++);
            repostDTO.setConteudo(repost.getPost().getConteudo());
            repostDTO.setUserId(repost.getUser().getId());
            repostDTO.setRepost(true);
            repostDTO.setPostId(repost.getPost().getId()); // Define o mesmo ID do post original
            User userRepostador = repost.getUser();
            repostDTO.setNomeRepostador(userRepostador.getNome());
            repostDTO.setSobrenomeRepostador(userRepostador.getSobrenome());
            repostDTO.setUsernameRepostador(userRepostador.getUsername());
            repostDTO.setAvatarRepostador(userRepostador.getAvatar());

            // Se for um repost, preencha as informações do usuário original
            if (repost.getPost() != null && repost.getPost().getAutor() != null) {
                User userOriginal = repost.getPost().getAutor();
                repostDTO.setNome(userOriginal.getNome());
                repostDTO.setSobrenome(userOriginal.getSobrenome());
                repostDTO.setUsername(userOriginal.getUsername());
                repostDTO.setAvatar(userOriginal.getAvatar());
            }

            todasPostagens.add(repostDTO);
        }

        return ResponseEntity.ok(todasPostagens);
    }

    @GetMapping("/repost")
    public ResponseEntity<List<Map<String, Long>>> obterTodosRepostInfo() {
        List<Map<String, Long>> repostInfo = repostService.obterTodosRepostInfo();
        return ResponseEntity.ok(repostInfo);
    }

    @GetMapping("/allposts")
    public ResponseEntity<List<Map<String, Object>>> obterTodasPostagensEReposts() {
        List<Map<String, Object>> postsAndReposts = postService.obterTodasPostagensEReposts();
        return ResponseEntity.ok(postsAndReposts);
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
