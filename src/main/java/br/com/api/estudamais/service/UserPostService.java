package br.com.api.estudamais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.dto.SubmittedPostDTO;
import br.com.api.estudamais.model.Community;
import br.com.api.estudamais.model.SubmittedPost;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.CommunityRepository;
import br.com.api.estudamais.repository.SubmittedPostRepository;
import br.com.api.estudamais.repository.UserRepository;

@Service
public class UserPostService {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmittedPostRepository submittedPostRepository;

    public boolean submitPostToCommunity(Long userId, Long communityId, SubmittedPostDTO submittedPostDTO) {
        try {
            // Verificar se o usuário e a comunidade existem
            User user = userRepository.findById(userId).orElse(null);
            Community community = communityRepository.findById(communityId).orElse(null);

            if (user != null && community != null) {
                // Verificar se o usuário é membro da comunidade
                if (community.getMembros().contains(user)) {
                    // Criar a postagem submetida pelo usuário
                    SubmittedPost submittedPost = new SubmittedPost();
                    submittedPost.setUser(user);
                    submittedPost.setPostContent(submittedPostDTO.getConteudo());
                    submittedPost.setCommunity(community);

                    // Salvar a postagem submetida
                    submittedPostRepository.save(submittedPost);
                    
                    return true; // Postagem submetida com sucesso
                } else {
                    // Usuário não é membro da comunidade
                    return false; // Postagem não autorizada
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Tratamento de exceções
        }
        return false; // Se algo der errado durante o processo
    }
}
