package br.com.api.estudamais.controller;

import br.com.api.estudamais.model.Community;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.CommunityRepository;
import br.com.api.estudamais.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SearchController {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    @Autowired
    public SearchController(UserRepository userRepository, CommunityRepository communityRepository) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Object>> searchUsersAndCommunities(@PathVariable("query") String query) {
        List<User> users = userRepository.findByNomeOrUsernameContainingIgnoreCase(query, query);
        List<Community> communities = communityRepository.findByNomeContainingIgnoreCase(query);

        List<Object> result = new ArrayList<>();
        result.addAll(users);
        result.addAll(communities);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/search/{query}")
    public ResponseEntity<List<User>> searchUsers(@PathVariable("query") String query) {
        List<User> users = userRepository.findByNomeOrUsernameContainingIgnoreCase(query, query);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/communities/search/{query}")
    public ResponseEntity<List<Community>> searchCommunities(@PathVariable("query") String query) {
        List<Community> communities = communityRepository.findByNomeContainingIgnoreCase(query);
        return ResponseEntity.ok(communities);
    }
}
