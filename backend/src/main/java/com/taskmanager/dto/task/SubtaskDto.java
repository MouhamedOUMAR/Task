package com.taskmanager.dto.task;

import lombok.Data;

@Data
public class SubtaskDto {
    private Long id;
    private String title;
    private boolean completed;
} 