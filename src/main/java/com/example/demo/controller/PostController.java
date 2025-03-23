package com.example.demo.controller;

import com.example.demo.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto postDto, Authentication authentication) {
        String username = authentication.getName();
        Post post = postService.createPost(postDto.getTitle(), postDto.getContent(), username);
        return ResponseEntity.ok(post);
    }

    // Obtener todos los posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Obtener un post por ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // Actualizar un post (solo el autor del post puede hacerlo)
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDto postDto, Authentication authentication) {
        String username = authentication.getName();
        Post updatedPost = postService.updatePost(id, postDto.getTitle(), postDto.getContent(), username);
        return ResponseEntity.ok(updatedPost);
    }

    // Eliminar un post (solo el autor del post puede hacerlo)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        postService.deletePost(id, username);
        return ResponseEntity.noContent().build();
    }
}


