package com.verivue.model.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskTypeEnum {

    NEWS_SCAN_TIME(1001, 1,"Scheduled article review."),
    REMOTEERROR(1002, 2,"Retry on third-party API call failure.");

    private final int taskType; // Corresponds to specific business logic
    private final int priority; // Business-level categorization
    private final String desc;  // Description details
}