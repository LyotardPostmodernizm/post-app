package com.project.postapp.controllers;

import com.project.postapp.models.Post;
import com.project.postapp.requests.PostCreateRequest;
import com.project.postapp.requests.PostUpdateRequest;
import com.project.postapp.responses.PostResponse;
import com.project.postapp.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable Long postId){
        return postService.getPost(postId);

    }
    @GetMapping                     //URI'daki parametreleri pars edip belirtilen değişkene atar @RequestParam
    public List<PostResponse> gellAllPosts(@RequestParam Optional<Long>userId){
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createPost(newPostRequest);
    }
    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest){
        return postService.updatePost(postId,postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }



}
