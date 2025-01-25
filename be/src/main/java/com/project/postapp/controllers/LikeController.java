package com.project.postapp.controllers;

import com.project.postapp.models.Like;
import com.project.postapp.requests.LikeCreateRequest;
import com.project.postapp.services.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request) {
        return likeService.createLike(request);
    }

    @GetMapping("/{likeId}")
    public Like getLike(@PathVariable Long likeId) {
        return likeService.getLike(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
    }
}
