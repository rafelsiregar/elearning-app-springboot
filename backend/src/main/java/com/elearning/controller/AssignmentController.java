package com.elearning.controller;

import com.elearning.dto.AssignmentDTO;
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

    private AssignmentDTO toDTO(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());
        return dto;
    }

    private Assignment toEntity(AssignmentDTO dto) {
        Assignment assignment = new Assignment();
        assignment.setId(dto.getId());
        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());
        return assignment;
    }

    @GetMapping
    public List<AssignmentDTO> getAllAssignments() {
        return assignmentService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentService.findById(id);
        return assignment.map(a -> ResponseEntity.ok(toDTO(a))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AssignmentDTO createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Assignment assignment = toEntity(assignmentDTO);
        Assignment savedAssignment = assignmentService.save(assignment);
        return toDTO(savedAssignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDTO assignmentDTO) {
        Optional<Assignment> assignmentOptional = assignmentService.findById(id);
        if (!assignmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Assignment assignment = assignmentOptional.get();
        assignment.setTitle(assignmentDTO.getTitle());
        assignment.setDescription(assignmentDTO.getDescription());
        assignment.setDueDate(assignmentDTO.getDueDate());
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(toDTO(updatedAssignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
