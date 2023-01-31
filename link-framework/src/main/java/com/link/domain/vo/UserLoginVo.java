package com.link.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Link
 * @Description
 * @date 2023-01-15 13:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVo {

    private String token;

    private UserInfoVo userInfo;
}
