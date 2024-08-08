package com.user.decision.service;

import com.user.decision.model.DecisionResponse;
import com.user.decision.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DecisionService {

    public DecisionResponse decisionResponse(UserRequest userRequest, DecisionResponse response){
        responseCodeStatus(userRequest,response);
        response.setDecisionExecutionDate(getCurrentDateTime());
        response.setRoutingIndicator(userRequest.userInfo.routingIndicator);
        return response;
    }

    private void responseCodeStatus(UserRequest userRequest, DecisionResponse response){
        if (userRequest.userInfo.userAge >= 18 && userRequest.userInfo.userAge<=60){
            response.setResponseCode("0000");
        }else if(userRequest.userInfo.userAge<18){
            response.setResponseCode("0001");
        }else {
            response.setResponseCode("0002");
        }
    }
    private String getCurrentDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the date and time as needed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Return the formatted date and time as a string
        return currentDateTime.format(formatter);
    }
}
