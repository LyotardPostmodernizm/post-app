package com.project.postapp.responses;

import com.project.postapp.models.Like;
import com.project.postapp.models.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String text;
    private String title;
    private List<LikeResponse> LikesOfPost;


    public PostResponse(Post entity, List<LikeResponse>likes){ //constructor mapper
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName = entity.getUser().getUsername();
        this.title = entity.getTitle();
        this.text = entity.getText();
        LikesOfPost = likes;
    }

}
