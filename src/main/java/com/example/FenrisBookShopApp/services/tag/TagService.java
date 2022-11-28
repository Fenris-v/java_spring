package com.example.FenrisBookShopApp.services.tag;

import com.example.FenrisBookShopApp.entities.other.TagEntity;
import com.example.FenrisBookShopApp.repositories.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagEntity> findAll() {
        return tagRepository.findAll();
    }

    public TagEntity findById(Long id) {
        return tagRepository.findTagEntityById(id);
    }
}
