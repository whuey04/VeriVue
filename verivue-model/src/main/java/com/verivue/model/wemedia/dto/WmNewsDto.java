package com.verivue.model.wemedia.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmNewsDto {
    
    private Long id;

    private String title;

    private Long channelId;

    private String labels;

    private Date publishTime;

    private String content;

    private Short type;

    private Date submitedTime; 

    private Short status;

    private List<String> images;

    private Short enable;
}