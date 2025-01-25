package com.project.postapp.services;

import com.project.postapp.models.Comment;
import com.project.postapp.models.Post;
import com.project.postapp.models.User;
import com.project.postapp.repositories.CommentRepository;
import com.project.postapp.requests.CommentCreateRequest;
import com.project.postapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;


    public CommentService(CommentRepository commentRepository,UserService userService,
                          PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) { //Ayrı ayrı userId, postId gelebilir ya da her ikisi gelebilir: Şu posta yapılmış commentler, şu userin commentleri, şu userin şu postunun commentleri gibi

        if(userId.isPresent() && postId.isPresent()){
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            return commentRepository.findByPostId(postId.get());
        }
        else return commentRepository.findAll(); //Eğer userId ve postId'nin her ikisi de gelmemişse, yani null ise tüm commentleri getir.

    }



    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUser(commentCreateRequest.getUserId());
        Post post = postService.getPost(commentCreateRequest.getPostId());
        if(user !=null && post != null){
           Comment commentToSave = new Comment();
           commentToSave.setID(commentCreateRequest.getId());
           commentToSave.setUser(user);
           commentToSave.setPost(post);
           commentToSave.setText(commentCreateRequest.getText());
           return commentRepository.save(commentToSave);
        }
        return null;
    }

    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest ) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
            return commentRepository.save(commentToUpdate);
        }
        return null;
    }


    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
