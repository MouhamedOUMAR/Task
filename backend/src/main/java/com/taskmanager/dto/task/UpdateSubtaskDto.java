package com.taskmanager.dto.task;

import lombok.Data;

@Data
public class UpdateSubtaskDto {
    private String title;
    private boolean completed;
} 