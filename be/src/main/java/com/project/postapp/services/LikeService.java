package com.project.postapp.services;

import com.project.postapp.models.Like;
import com.project.postapp.models.Post;
import com.project.postapp.models.User;
import com.project.postapp.repositories.LikeRepository;
import com.project.postapp.requests.LikeCreateRequest;
import com.project.postapp.responses.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    @Autowired
    @Lazy
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService,
                       PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public Like getLike(Long LikeId) {
        return likeRepository.findById(LikeId).orElse(null);
    }

    public Like createLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUser(likeCreateRequest.getUserId());
        Post post = postService.getPost(likeCreateRequest.getPostId());
        if(user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            return likeRepository.save(likeToSave);
        }
            return null;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(LikeResponse::new).collect(Collectors.toList());
    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }



}
