package com.hejazi.securityApp.securityApp.controllers;

import com.hejazi.securityApp.securityApp.dto.PostDTO;
import com.hejazi.securityApp.securityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @GetMapping(path = "/{postId}")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PutMapping(path = "/{postId}")
    public PostDTO updatePost(@RequestBody PostDTO inputPost,@PathVariable Long postId){
        return postService.updatePost(inputPost, postId);
    }
}
