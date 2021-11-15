package com.gteam.glog.post.service;

import com.gteam.glog.domain.dto.post.PostContentsDTO;
import com.gteam.glog.domain.dto.post.PostRequestDTO;
import com.gteam.glog.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String createPost(PostRequestDTO postRequestDTO, String id) {
        PostContentsDTO postContentsDTO = new PostContentsDTO();

        postContentsDTO.setContents(postRequestDTO.getContents());
        postContentsDTO.setTitle(postRequestDTO.getTitle());
        postContentsDTO.setNikNm(postRequestDTO.getNikNm());
        postContentsDTO.setImgNm(postRequestDTO.getImgNm());
        postContentsDTO.setId(id);

        for(String tag: postRequestDTO.getTag()) {
            postContentsDTO.setTag(postContentsDTO.getTag() + tag);
        }
        return postRepository.createPost(postContentsDTO);
    }

    public PostContentsDTO updatePost(int idx) {
        return postRepository.updatePost(idx);
    }

    public PostContentsDTO detailsPost(int idx) {
        return postRepository.detailPost(idx);
    }

    public String deletePost(PostRequestDTO requestDTO) {
        return postRepository.deletePost(requestDTO.getIdx());
    }
}
