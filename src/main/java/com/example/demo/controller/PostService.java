package com.example.demo.controller;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(String title, String content, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
    }

    public List<Post> getPostsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return postRepository.findByUser(user);
    }

    // Actualizar un post (solo el autor puede hacerlo)
    public Post updatePost(Long id, String newTitle, String newContent, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permiso para actualizar este post");
        }

        post.setTitle(newTitle);
        post.setContent(newContent);
        return postRepository.save(post);
    }

    // Eliminar un post (solo el autor puede hacerlo)
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permiso para eliminar este post");
        }

        postRepository.delete(post);
    }

}

