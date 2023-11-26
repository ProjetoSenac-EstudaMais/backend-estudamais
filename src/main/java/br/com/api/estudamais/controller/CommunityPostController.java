package br.com.api.estudamais.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.estudamais.dto.SubmittedPostDTO;
import br.com.api.estudamais.service.CommunityService;
import br.com.api.estudamais.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/communities/{communityId}/posts")
public class CommunityPostController {

    private final CommunityService communityService;
    public CommunityPostController(CommunityService communityService, UserService userService) {
        this.communityService = communityService;
    }

    @PostMapping("/submit/{userId}")
    public ResponseEntity<String> submitPostToCommunity(
            @PathVariable Long communityId,
            @PathVariable Long userId,
            @RequestBody SubmittedPostDTO submittedPostDTO) {
    
        // Use o userId recebido para processar a postagem para a comunidade
        boolean posted = communityService.submitPostToCommunity(communityId, userId, submittedPostDTO);
    
        if (posted) {
            return ResponseEntity.ok("Postagem submetida com sucesso para a comunidade!");
        } else {
            return ResponseEntity.badRequest().body("Falha ao submeter a postagem para a comunidade.");
        }
    }
    
    
}