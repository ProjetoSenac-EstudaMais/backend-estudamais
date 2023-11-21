package br.com.api.estudamais.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.model.ApprovedPost;
import br.com.api.estudamais.model.Community;
import br.com.api.estudamais.model.SubmittedPost;
import br.com.api.estudamais.repository.ApprovedPostRepository;
import br.com.api.estudamais.repository.CommunityRepository;
import br.com.api.estudamais.repository.SubmittedPostRepository;

@Service
public class SubmittedPostService {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private SubmittedPostRepository submittedPostRepository;

    @Autowired
    private ApprovedPostRepository approvedPostRepository;

    public SubmittedPost save(SubmittedPost submittedPost, Long communityId) {
        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null) {
            submittedPost.setCommunity(community);
            return submittedPostRepository.save(submittedPost);
        } else {
            return null;
        }
    }

    public List<SubmittedPost> getAllPendingPostsByCommunityId(Long communityId) {
        return submittedPostRepository.findByCommunityIdAndApprovedFalse(communityId);
    }

    public boolean approvePost(Long postId, Long communityId) {
        Optional<SubmittedPost> postOptional = submittedPostRepository.findById(postId);
        Optional<Community> communityOptional = communityRepository.findById(communityId);

        if (postOptional.isPresent() && communityOptional.isPresent()) {
            SubmittedPost post = postOptional.get();
            Community community = communityOptional.get();

            // Aprovar o post
            post.setApproved(true);
            post.setCommunity(community);

            submittedPostRepository.save(post);

            // Criar um objeto ApprovedPost
            ApprovedPost approvedPost = new ApprovedPost();
            approvedPost.setTitulo(post.getTitle());
            approvedPost.setConteudo(post.getConteudo());
            approvedPost.setCommunity(community);

            // Persistir o post aprovado
            approvedPostRepository.save(approvedPost);

            // Adicionar à lista de postagens aprovadas da comunidade
            community.getApprovedPosts().add(approvedPost);
            communityRepository.save(community);

            return true;
        }

        return false;
    }

    public boolean rejectPost(Long postId) {
        Optional<SubmittedPost> postOptional = submittedPostRepository.findById(postId);

        if (postOptional.isPresent()) {
            SubmittedPost post = postOptional.get();
            submittedPostRepository.delete(post);
            return true;
        }

        return false;
    }

}
