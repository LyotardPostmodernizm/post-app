package com.project.postapp.responses;

import com.project.postapp.models.Like;
import lombok.Data;

@Data
public class LikeResponse {

    private Long id;
    private Long userId;
    private Long postId;

    public LikeResponse(Like entity) {
        this.id = entity.getID();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
    }




}
