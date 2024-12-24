package com.kafka.user.transformation;

import com.kafka.user.constants.Constants;
import com.kafka.user.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class TransformData {
    public String eventStatus(UserInfo userInfo) {
        if (userInfo.userAge < 18) {
            return Constants.FAILED;
        } else if (userInfo.userAge > 18 && userInfo.userAge < 25) {
            return Constants.PENDING;
        } else {
            return Constants.SUCCESS;
        }
    }

    public String userName(UserInfo userInfo) {
        return userInfo.userName.firstName + " " + userInfo.userName.middleName + " " + userInfo.userName.lastName;
    }

    public String uniqueEventId(UserInfo userInfo) {
        return userInfo.userId + userInfo.userName.lastName + userInfo.userAddress.pinCode + userInfo.userProof.aadharNo + java.time.Clock.systemUTC().instant();
    }
}
