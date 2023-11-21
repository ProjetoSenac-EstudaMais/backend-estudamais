package br.com.api.estudamais.controller;

import java.util.ArrayList;
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

import br.com.api.estudamais.dto.SubmittedPostDTO;
import br.com.api.estudamais.model.ApprovedPost;
import br.com.api.estudamais.model.Community;
import br.com.api.estudamais.model.Quiz;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.service.CommunityService;
import br.com.api.estudamais.service.UserService;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunities() {
        List<Community> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<Community> getCommunityById(@PathVariable Long communityId) {
        Community community = communityService.getCommunityById(communityId);
        return community != null ? ResponseEntity.ok(community) : ResponseEntity.notFound().build();
    }

     @GetMapping("/{communityId}/posts")
    public ResponseEntity<List<ApprovedPost>> getAllApprovedPostsByCommunityId(@PathVariable Long communityId) {
        List<ApprovedPost> approvedPosts = communityService.getAllApprovedPostsByCommunityId(communityId);
        if (!approvedPosts.isEmpty()) {
            return ResponseEntity.ok(approvedPosts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{communityId}/quizzes")
    public ResponseEntity<List<Quiz>> getQuizzesByCommunity(@PathVariable Long communityId) {
        List<Quiz> quizzes = communityService.getQuizzesByCommunity(communityId);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Community> createCommunity(@RequestBody Community community, @PathVariable Long userId) {
        // Cria uma lista com o usuário recebido pelo userId
        List<User> administrators = new ArrayList<>();
        User adminUser = userService.getUserById(userId); // Obter o usuário pelo ID, substitua userService pelo seu
                                                          // serviço
        administrators.add(adminUser);

        // Configura a lista de administradores para a comunidade
        community.setAdministradores(administrators);

        // Cria a comunidade
        Community newCommunity = communityService.createCommunity(community);

        // Retorna a resposta com a nova comunidade criada
        return ResponseEntity.status(HttpStatus.CREATED).body(newCommunity);
    }

    // Adicionar um membro a uma comunidade
    @PostMapping("/{communityId}/members/{memberId}")
    public ResponseEntity<Community> addMemberToCommunity(
            @PathVariable Long communityId,
            @PathVariable Long memberId) {

        Community updatedCommunity = communityService.addMemberToCommunity(communityId, memberId);
        if (updatedCommunity != null) {
            return ResponseEntity.ok(updatedCommunity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/submit/{userId}") // Adicione o userId como parte do caminho
    public ResponseEntity<String> submitPostToCommunity(
            @PathVariable Long communityId,
            @PathVariable Long userId,
            @RequestBody SubmittedPostDTO submittedPostDTO) {

        User user = userService.getUserById(userId); // Obter usuário pelo ID

        if (user != null) {
            boolean posted = communityService.submitPostToCommunity(communityId, userId, submittedPostDTO);

            if (posted) {
                return ResponseEntity.ok("Postagem submetida com sucesso para a comunidade!");
            } else {
                return ResponseEntity.badRequest().body("Falha ao submeter a postagem para a comunidade.");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuário não encontrado para submeter a postagem.");
        }
    }

}
