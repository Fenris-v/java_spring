package com.example.FenrisBookShopApp.dto.tag;

import com.example.FenrisBookShopApp.entities.other.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagCountDto {
    private TagEntity tag;
    private Long usingCount;
}
