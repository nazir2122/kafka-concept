package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {
    private static final long serialVersionUID = 6624075166255122029L;
	public UserInfo userInfo;
    public UserResponse userResponse;
}
