package com.verivue.model.wemedia.vo;

import lombok.Data;

@Data
public class WmUserVo {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String phone;

    private String image;

    private Integer type;
}
