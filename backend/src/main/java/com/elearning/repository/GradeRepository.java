package com.elearning.repository;

import com.elearning.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findByQuizId(Long quizId);
    List<Grade> findByAssignmentId(Long assignmentId);
}
