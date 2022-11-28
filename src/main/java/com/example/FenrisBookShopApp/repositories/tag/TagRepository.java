package com.example.FenrisBookShopApp.repositories.tag;

import com.example.FenrisBookShopApp.dto.tag.TagCountDto;
import com.example.FenrisBookShopApp.entities.other.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    @Query("select new com.example.FenrisBookShopApp.dto.tag.TagCountDto(t, count(b2t)) from TagEntity t " +
            "left join Book2TagEntity b2t on t.id = b2t.tagId group by t.id")
    List<TagCountDto> findAllWithUsingCount();

    TagEntity findTagEntityById(Long id);
}
