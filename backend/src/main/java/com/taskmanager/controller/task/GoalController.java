package com.taskmanager.controller.task;

import com.taskmanager.dto.task.*;
import com.taskmanager.entity.User;
import com.taskmanager.service.task.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<List<GoalDto>> getGoals(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(goalService.getGoals(user));
    }

    @PostMapping
    public ResponseEntity<GoalDto> createGoal(@RequestBody CreateGoalDto goalDto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(goalService.createGoal(goalDto, user));
    }

    @PostMapping("/{goalId}/subtasks")
    public ResponseEntity<SubtaskDto> createSubtask(
            @PathVariable Long goalId,
            @RequestBody CreateSubtaskDto subtaskDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(goalService.createSubtask(goalId, subtaskDto, user));
    }

    @PutMapping("/{goalId}/subtasks/{subtaskId}")
    public ResponseEntity<SubtaskDto> updateSubtask(
            @PathVariable Long goalId,
            @PathVariable Long subtaskId,
            @RequestBody UpdateSubtaskDto subtaskDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(goalService.updateSubtask(goalId, subtaskId, subtaskDto, user));
    }
} 