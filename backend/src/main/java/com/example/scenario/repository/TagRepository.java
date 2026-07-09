package com.example.scenario.repository;

import com.example.scenario.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByCategory(String category);
    @Query("SELECT DISTINCT t.category FROM Tag t ORDER BY t.category")
    List<String> findDistinctCategories();
}