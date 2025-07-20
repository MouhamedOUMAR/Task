package com.taskmanager.dto.task;

import lombok.Data;
import java.util.List;

@Data
public class GoalDto {
    private Long id;
    private String title;
    private String description;
    private List<SubtaskDto> subtasks;
    private int completionPercentage;
} 