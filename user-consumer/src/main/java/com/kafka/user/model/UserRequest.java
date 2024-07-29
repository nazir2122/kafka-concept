package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {
    public UserInfo userInfo;
    public UserResponse userResponse;
}
