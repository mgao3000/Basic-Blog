package com.devmountain.blog.repository;

import com.devmountain.blog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p left join fetch p.author order by p.date DESC")
    List<Post> findLatest5Posts(Pageable pageable);

    @Query("select p from Post p left join fetch p.author where p.id= :postId")
    Post findPostWithAuthorInfo(Long postId);
}
