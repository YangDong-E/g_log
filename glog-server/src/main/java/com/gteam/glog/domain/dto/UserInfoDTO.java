package com.gteam.glog.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private int usrIdx;
    private String userId;
    private String userPwd;
    private String nikName;
    private String glogTitle;
    private String imgName;
}