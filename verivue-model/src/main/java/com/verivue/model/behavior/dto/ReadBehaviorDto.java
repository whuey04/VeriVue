package com.verivue.model.behavior.dto;
 
import lombok.Data;
 
@Data
public class ReadBehaviorDto {

    Long articleId;

    Short count;

    Integer readDuration;

    Short percentage;

    Short loadDuration;

}