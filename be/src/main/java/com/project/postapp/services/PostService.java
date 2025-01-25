package com.project.postapp.services;

import com.project.postapp.models.Like;
import com.project.postapp.models.Post;
import com.project.postapp.models.User;
import com.project.postapp.repositories.PostRepository;
import com.project.postapp.requests.PostCreateRequest;
import com.project.postapp.requests.PostUpdateRequest;
import com.project.postapp.responses.LikeResponse;
import com.project.postapp.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;
    @Autowired
    @Lazy
    private LikeService likeService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Autowired
    public void setLikeService(LikeService likeService){
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> postList;
        if(userId.isPresent()){
            postList = postRepository.findByUserId(userId.get());
        }
        else{
            postList= postRepository.findAll();
        }
        return postList.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p, likes);
        }).collect(Collectors.toList());
    }


    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createPost(PostCreateRequest newPostRequest) {
        User user = userService.getUser(newPostRequest.getUserId());
        if(user == null) return null;
        Post post = new Post();
        post.setId(newPostRequest.getId());
        post.setTitle(newPostRequest.getTitle());
        post.setText(newPostRequest.getText());
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){                           //Gelen istekte, veritabanındaki bir postId mi isteniyor diye kontrol
            Post toUpdate = post.get();
            toUpdate.setTitle(postUpdateRequest.getTitle());
            toUpdate.setText(postUpdateRequest.getText());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
