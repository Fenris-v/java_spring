package com.example.FenrisBookShopApp.shedules;

import com.example.FenrisBookShopApp.dto.tag.TagCountDto;
import com.example.FenrisBookShopApp.entities.other.TagEntity;
import com.example.FenrisBookShopApp.repositories.tag.TagRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagUsingCounter {
    private final TagRepository tagRepository;
    private final List<TagEntity> countedTags = new ArrayList<>();

    @Autowired
    public TagUsingCounter(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Scheduled(cron = "0 0 4 * * *", zone = "Europe/Moscow")
    private void count() {
        List<TagCountDto> tags = tagRepository.findAllWithUsingCount();
        tags.forEach(this::calculateTagUsing);

        tagRepository.saveAll(countedTags);
        countedTags.clear();
    }

    private void calculateTagUsing(@NotNull TagCountDto tagCountDto) {
        TagEntity tag = tagCountDto.getTag();
        tag.setUsed(tagCountDto.getUsingCount());
        countedTags.add(tag);

        if (countedTags.size() >= 100) {
            tagRepository.saveAll(countedTags);
            countedTags.clear();
        }
    }
}
