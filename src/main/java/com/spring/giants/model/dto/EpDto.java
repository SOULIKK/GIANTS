package com.spring.giants.model.dto;


import com.spring.giants.model.entity.EditorsPick;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EpDto {

    private Long epId;
    private String title;
    private String description;
    private String url;
    private String thumbnail;
    private LocalDateTime createdAt;


    public EpDto(EditorsPick editorsPick) {
        this.epId = editorsPick.getEpId();
        this.title = editorsPick.getTitle();
        this.description = editorsPick.getDescription();
        this.url = editorsPick.getUrl();
        this.thumbnail = editorsPick.getThumbnail();
        this.createdAt = editorsPick.getCreatedAt();
    }
}
