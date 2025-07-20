package com.taskmanager.service.task;

import com.taskmanager.dto.task.*;
import com.taskmanager.entity.Goal;
import com.taskmanager.entity.Subtask;
import com.taskmanager.entity.User;
import com.taskmanager.repository.GoalRepository;
import com.taskmanager.repository.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final SubtaskRepository subtaskRepository;
    private final ModelMapper modelMapper;

    public List<GoalDto> getGoals(User user) {
        return goalRepository.findByUserId(user.getId())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public GoalDto createGoal(CreateGoalDto goalDto, User user) {
        Goal goal = new Goal();
        goal.setTitle(goalDto.getTitle());
        goal.setDescription(goalDto.getDescription());
        goal.setUser(user);
        goalRepository.save(goal);
        return convertToDto(goal);
    }

    public SubtaskDto createSubtask(Long goalId, CreateSubtaskDto subtaskDto, User user) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        Subtask subtask = new Subtask();
        subtask.setTitle(subtaskDto.getTitle());
        subtask.setCompleted(false);
        subtask.setGoal(goal);
        subtaskRepository.save(subtask);
        return modelMapper.map(subtask, SubtaskDto.class);
    }

    public SubtaskDto updateSubtask(Long goalId, Long subtaskId, UpdateSubtaskDto subtaskDto, User user) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        if (!goal.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }
        Subtask subtask = subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new RuntimeException("Subtask not found"));
        if (!subtask.getGoal().getId().equals(goalId)) {
            throw new RuntimeException("Subtask does not belong to this goal");
        }
        subtask.setTitle(subtaskDto.getTitle());
        subtask.setCompleted(subtaskDto.isCompleted());
        subtaskRepository.save(subtask);
        return modelMapper.map(subtask, SubtaskDto.class);
    }

    private GoalDto convertToDto(Goal goal) {
        GoalDto goalDto = modelMapper.map(goal, GoalDto.class);
        goalDto.setCompletionPercentage(calculateCompletionPercentage(goal));
        return goalDto;
    }

    private int calculateCompletionPercentage(Goal goal) {
        if (goal.getSubtasks() == null || goal.getSubtasks().isEmpty()) {
            return 0;
        }
        long completedCount = goal.getSubtasks().stream().filter(Subtask::isCompleted).count();
        return (int) ((completedCount * 100) / goal.getSubtasks().size());
    }
}
