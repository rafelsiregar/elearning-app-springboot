package com.elearning.controller;

import com.elearning.dto.GradeDTO;
import com.elearning.model.Grade;
import com.elearning.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    private GradeDTO toDTO(Grade grade) {
        GradeDTO dto = new GradeDTO();
        dto.setId(grade.getId());
        dto.setStudentId(grade.getStudentId());
        dto.setQuizId(grade.getQuizId());
        dto.setAssignmentId(grade.getAssignmentId());
        dto.setScore(grade.getScore());
        return dto;
    }

    private Grade toEntity(GradeDTO dto) {
        Grade grade = new Grade();
        grade.setId(dto.getId());
        grade.setStudentId(dto.getStudentId());
        grade.setQuizId(dto.getQuizId());
        grade.setAssignmentId(dto.getAssignmentId());
        grade.setScore(dto.getScore());
        return grade;
    }

    @GetMapping
    public List<GradeDTO> getAllGrades() {
        return gradeService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable Long id) {
        Optional<Grade> grade = gradeService.findById(id);
        return grade.map(g -> ResponseEntity.ok(toDTO(g))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public GradeDTO createGrade(@RequestBody GradeDTO gradeDTO) {
        Grade grade = toEntity(gradeDTO);
        Grade savedGrade = gradeService.save(grade);
        return toDTO(savedGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable Long id, @RequestBody GradeDTO gradeDTO) {
        Optional<Grade> gradeOptional = gradeService.findById(id);
        if (!gradeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Grade grade = gradeOptional.get();
        grade.setStudentId(gradeDTO.getStudentId());
        grade.setQuizId(gradeDTO.getQuizId());
        grade.setAssignmentId(gradeDTO.getAssignmentId());
        grade.setScore(gradeDTO.getScore());
        Grade updatedGrade = gradeService.save(grade);
        return ResponseEntity.ok(toDTO(updatedGrade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
