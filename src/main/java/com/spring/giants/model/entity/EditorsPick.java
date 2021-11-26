package com.spring.giants.model.entity;


import com.spring.giants.model.dto.EpDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class EditorsPick extends Timestamped {

    @Id
    private Long ep_id;
    private String title;
    private String description;
    private String url;
    private String thumbnail;

    public EditorsPick(EpDto epDto) {
        this.ep_id = epDto.getEp_id();
        this.title = epDto.getTitle();
        this.description = epDto.getDescription();
        this.url = epDto.getUrl();
        this.thumbnail = epDto.getThumbnail();
    }

}
