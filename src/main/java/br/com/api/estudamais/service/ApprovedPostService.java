package br.com.api.estudamais.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.api.estudamais.model.ApprovedPost;
import br.com.api.estudamais.repository.ApprovedPostRepository;

import java.util.List;

@Service
public class ApprovedPostService {

    @Autowired
    private ApprovedPostRepository approvedPostRepository;

    public List<ApprovedPost> getAllApprovedPosts() {
        return approvedPostRepository.findAll();
    }


    public ApprovedPost createApprovedPost(ApprovedPost approvedPost) {
        return approvedPostRepository.save(approvedPost);
    }

    public ApprovedPost getApprovedPostById(Long id) {
        return approvedPostRepository.findById(id).orElse(null);
    }
}
