package com.elearning.service;

import com.elearning.wrapper.GradeWrapper;
import java.util.List;
import java.util.Optional;

public interface GradeService {
    List<GradeWrapper> findAll();
    
    Optional<GradeWrapper> findById(Long id);
    
    GradeWrapper save(GradeWrapper gradeWrapper);
    
    void deleteById(Long id);
    
    List<GradeWrapper> findByStudentId(Long studentId);
    
    List<GradeWrapper> findByQuizId(Long quizId);
    
    List<GradeWrapper> findByAssignmentId(Long assignmentId);
}
