package com.verivue.model.comment.vo;
 
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
 
@Data
public class CountVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long countNum;
}