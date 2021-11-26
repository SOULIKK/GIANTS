package com.spring.giants.model.dto;


import com.spring.giants.model.entity.EditorsPick;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EpDto {

    private Long ep_id;
    private String title;
    private String description;
    private String url;
    private String thumbnail;
    private LocalDateTime createdAt;

    public EpDto(EditorsPick editorsPick) {
        this.ep_id = editorsPick.getEp_id();
        this.title = editorsPick.getTitle();
        this.description = editorsPick.getDescription();
        this.url = editorsPick.getUrl();
        this.thumbnail = editorsPick.getThumbnail();
        this.createdAt = editorsPick.getCreatedAt();
    }

}
