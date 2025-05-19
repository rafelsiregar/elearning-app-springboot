package com.elearning.controller;

import com.elearning.wrapper.AssignmentWrapper;
import com.elearning.model.Assignment;
import com.elearning.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    private AssignmentWrapper toWrapper(Assignment assignment) {
        AssignmentWrapper wrapper = new AssignmentWrapper();
        wrapper.setId(assignment.getId());
        wrapper.setTitle(assignment.getTitle());
        wrapper.setDescription(assignment.getDescription());
        wrapper.setDueDate(assignment.getDueDate());
        return wrapper;
    }

    private Assignment toEntity(AssignmentWrapper wrapper) {
        Assignment assignment = new Assignment();
        assignment.setId(wrapper.getId());
        assignment.setTitle(wrapper.getTitle());
        assignment.setDescription(wrapper.getDescription());
        assignment.setDueDate(wrapper.getDueDate());
        return assignment;
    }

    @GetMapping
    public List<AssignmentWrapper> getAllAssignments() {
        return assignmentService.findAll().stream()
            .map(this::toWrapper)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentWrapper> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.findById(id);
        return assignment.map(a -> ResponseEntity.ok(toWrapper(a)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AssignmentWrapper createAssignment(@RequestBody AssignmentWrapper assignmentWrapper) {
        Assignment assignment = toEntity(assignmentWrapper);
        Assignment savedAssignment = assignmentService.save(assignment);
        return toWrapper(savedAssignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentWrapper> updateAssignment(
            @PathVariable Long id,
            @RequestBody AssignmentWrapper assignmentWrapper) {
        Optional<Assignment> assignmentOptional = assignmentService.findById(id);
        if (!assignmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Assignment assignment = assignmentOptional.get();
        assignment.setTitle(assignmentWrapper.getTitle());
        assignment.setDescription(assignmentWrapper.getDescription());
        assignment.setDueDate(assignmentWrapper.getDueDate());
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(toWrapper(updatedAssignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
