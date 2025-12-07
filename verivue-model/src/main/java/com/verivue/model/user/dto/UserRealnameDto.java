package com.verivue.model.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UserRealnameDto {

    private String name;

    private String idno;

    private Integer type;

    private String fontImage;

    private String backImage;

    private String holdImage;

    private String liveImage;


}
