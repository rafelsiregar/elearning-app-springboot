package com.elearning.controller;

import com.elearning.wrapper.GradeWrapper;
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

    private GradeWrapper toWrapper(Grade grade) {
        GradeWrapper wrapper = new GradeWrapper();
        wrapper.setId(grade.getId());
        wrapper.setStudentId(grade.getStudentId());
        wrapper.setQuizId(grade.getQuizId());
        wrapper.setAssignmentId(grade.getAssignmentId());
        wrapper.setScore(grade.getScore());
        return wrapper;
    }

    private Grade toEntity(GradeWrapper wrapper) {
        Grade grade = new Grade();
        grade.setId(wrapper.getId());
        grade.setStudentId(wrapper.getStudentId());
        grade.setQuizId(wrapper.getQuizId());
        grade.setAssignmentId(wrapper.getAssignmentId());
        grade.setScore(wrapper.getScore());
        return grade;
    }

    @GetMapping
    public List<GradeWrapper> getAllGrades() {
        return gradeService.findAll().stream()
            .map(this::toWrapper)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeWrapper> getGradeById(@PathVariable Long id) {
        Optional<Grade> grade = gradeService.findById(id);
        return grade.map(g -> ResponseEntity.ok(toWrapper(g)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public GradeWrapper createGrade(@RequestBody GradeWrapper gradeWrapper) {
        Grade grade = toEntity(gradeWrapper);
        Grade savedGrade = gradeService.save(grade);
        return toWrapper(savedGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeWrapper> updateGrade(
            @PathVariable Long id,
            @RequestBody GradeWrapper gradeWrapper) {
        Optional<Grade> gradeOptional = gradeService.findById(id);
        if (!gradeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Grade grade = gradeOptional.get();
        grade.setStudentId(gradeWrapper.getStudentId());
        grade.setQuizId(gradeWrapper.getQuizId());
        grade.setAssignmentId(gradeWrapper.getAssignmentId());
        grade.setScore(gradeWrapper.getScore());
        Grade updatedGrade = gradeService.save(grade);
        return ResponseEntity.ok(toWrapper(updatedGrade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
