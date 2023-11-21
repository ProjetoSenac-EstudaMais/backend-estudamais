package br.com.api.estudamais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.estudamais.dto.SubmittedPostDTO;
import br.com.api.estudamais.model.ApprovedPost;
import br.com.api.estudamais.model.Community;
import br.com.api.estudamais.model.Quiz;
import br.com.api.estudamais.model.SubmittedPost;
import br.com.api.estudamais.model.User;
import br.com.api.estudamais.repository.ApprovedPostRepository;
import br.com.api.estudamais.repository.CommunityRepository;
import br.com.api.estudamais.repository.SubmittedPostRepository;
import br.com.api.estudamais.repository.UserRepository;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmittedPostRepository submittedPostRepository;

    @Autowired
    private ApprovedPostRepository approvedPostRepository;

    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    public Community getCommunityById(Long id) {
        return communityRepository.findById(id).orElse(null);
    }

    public List<Quiz> getQuizzesByCommunity(Long communityId) {
        return null;
    }

    public Community addMemberToCommunity(Long communityId, Long memberId) {
        Community community = communityRepository.findById(communityId).orElse(null);
        User member = userRepository.findById(memberId).orElse(null);

        if (community != null && member != null) {
            community.getMembros().add(member);
            member.getCommunities().add(community);
            communityRepository.save(community);
            userRepository.save(member);
            return community;
        } else {
            return null;
        }
    }

    public boolean submitPostToCommunity(Long communityId, Long userId, SubmittedPostDTO submittedPostDTO) {
        Community community = communityRepository.findById(communityId).orElse(null);

        if (community != null) {
            SubmittedPost submittedPost = convertToSubmittedPost(submittedPostDTO);
            submittedPost.setCommunity(community);
            submittedPost.setApproved(false);
        
            submittedPostRepository.save(submittedPost);
        
            return true;
        } else {
            return false;
        }
    }

    private SubmittedPost convertToSubmittedPost(SubmittedPostDTO submittedPostDTO) {
        SubmittedPost submittedPost = new SubmittedPost();
        submittedPost.setConteudo(submittedPostDTO.getConteudo());
        return submittedPost;
    }

    public boolean isMemberOfCommunity(Long userId, Long communityId) {
        Community community = communityRepository.findById(communityId).orElse(null);
        if (community != null) {
            return community.getMembros().stream().anyMatch(user -> user.getId().equals(userId));
        }
        return false;
    }

      public List<ApprovedPost> getAllApprovedPostsByCommunityId(Long communityId) {
        return approvedPostRepository.findByCommunityId(communityId);
    }

}
