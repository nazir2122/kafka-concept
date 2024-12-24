package com.kafka.user.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class DecisionResponse implements Serializable {
    public String responseCode; //0000-success,0001-failed,0002-alerts
    public String decisionExecutionDate;
    public String routingIndicator;
    public String decisionStatus;
}
