package com.devmountain.blog.controller;

import com.devmountain.blog.model.Post;
import com.devmountain.blog.model.User;
import com.devmountain.blog.service.NotificationService;
import com.devmountain.blog.service.PostService;
import com.devmountain.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/posts/view/{id}")
    public String getPostViewByPostId(@PathVariable("id") Long postId, Model model) {
        Post post = postService.findById(postId);
        if (post == null) {
            notificationService.addErrorMessage("Cannot find post #" + postId);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/view";
    }

    /**
     * Display form of creating a post
     */
    @GetMapping("/posts/create")
    public ModelAndView displayCreatePostForm() {
        ModelAndView modelAndView = new ModelAndView();
        Post post = new Post();
        modelAndView.addObject(post);
        modelAndView.setViewName("posts/create");
        return modelAndView;
    }

    /**
     * Post request to create a post
     */
    @PostMapping("/posts/create")
    public ModelAndView createPost(@Valid Post post, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("posts/create");

        if (post.getTitle().isEmpty()) {
            bindingResult.rejectValue("title", "error.post", "Title cannot be empty");
        }
        if (post.getBody().isEmpty()) {
            bindingResult.rejectValue("body", "error.post", "Content cannot be empty");
        }
        // Get author
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUserName(auth.getName());
        if( user==null ){
            bindingResult.rejectValue("author", "error.post", "Author cannot be null");
        }
        if( !bindingResult.hasErrors() ){
            post.setAuthor(user);
            this.postService.create(post);
            modelAndView.addObject("successMessage", "Post has been created");
            modelAndView.addObject("post", new Post());
        }
        return modelAndView;
    }

    /**
     * delete a post by postId
     */
    @GetMapping("/posts/delete/{id}")
    public String deletePostById(@PathVariable("id") Long postId) {
        notificationService.removeMessage();

        Post post = this.postService.findById(postId);
        if (post == null)
            notificationService.addErrorMessage("Cannot find post with id=" + postId);
        else {
            if(!isAuthenticatedUserTheAuthorOfThePost(post))
                notificationService.addInfoMessage("Only the post author can delete his/her own post");
            else
                this.postService.deleteById(postId);
        }
        return "redirect:/posts/";
    }

    /**
     * Display a post for editing
     */
    @GetMapping("/posts/edit/{id}")
    public String getPostById(@PathVariable("id") Long postId, Model model) {
        notificationService.removeMessage();

        Post post = this.postService.findById(postId);
        if (post == null) {
            notificationService.addErrorMessage("Cannot find a post with postId=" + postId);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/edit")
    public ModelAndView update(@Valid Post post, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("posts/edit");

        if(post.getTitle().isEmpty())
            bindingResult.rejectValue("title", "error.post", "Title cannot be empty");
        if(post.getBody().isEmpty())
            bindingResult.rejectValue("body", "error.post", "Content cannot be empty");

        if(!isAuthenticatedUserTheAuthorOfThePost(post, bindingResult)) {
            bindingResult.rejectValue("id", "error.post", "Only the post author can edit the post");
            modelAndView.addObject("notAuthorizedUserMessage", "Only the post author can edit his/her own post");
        }

        if(!bindingResult.hasErrors()) {
            Post oriPost = this.postService.findPostWithAuthorById(post.getId());
            post.setAuthor(oriPost.getAuthor()); //???
            this.postService.edit(post);
            modelAndView.addObject("successMessage", "Post has been updated");
            modelAndView.addObject("post", post);
        }
        return modelAndView;
    }

    @GetMapping("/posts")
    public String index(Model model,
           @PageableDefault(sort = {"id"}, value = 3)Pageable pageable) {
        Page<Post> posts = this.postService.findAll(pageable);

        model.addAttribute("posts", posts);
        return "posts/index";
    }

    private boolean isAuthenticatedUserTheAuthorOfThePost(Post post, BindingResult bindingResult) {
        boolean isTheAuthor = false;
        // Get current login author
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = this.userService.findByUserName(auth.getName());
        if( authenticatedUser==null ){
            bindingResult.rejectValue("author", "error.post", "Authenticated User cannot be null");
        }

        Post oriPost = this.postService.findPostWithAuthorById(post.getId());
        if(Objects.equals(oriPost.getAuthor().getId(), authenticatedUser.getId())) {
            isTheAuthor = true;
        }
        return isTheAuthor;
    }

    private boolean isAuthenticatedUserTheAuthorOfThePost(Post post) {
        boolean isTheAuthor = false;
        // Get current login author
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = this.userService.findByUserName(auth.getName());
        if( authenticatedUser!=null ){
            Post oriPost = this.postService.findPostWithAuthorById(post.getId());
            if(Objects.equals(oriPost.getAuthor().getId(), authenticatedUser.getId()))
                isTheAuthor = true;
        }
        return isTheAuthor;
    }
}

