package com.elearning.service.impl;

import com.elearning.model.Grade;
import com.elearning.repository.GradeRepository;
import com.elearning.service.GradeService;
import com.elearning.wrapper.GradeWrapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public GradeWrapper toWrapper(Grade model) {
        if (model == null) {
            return null;
        }
        
        GradeWrapper wrapper = new GradeWrapper();
        wrapper.setId(model.getId());
        wrapper.setStudentId(model.getStudentId());
        wrapper.setQuizId(model.getQuizId());
        wrapper.setAssignmentId(model.getAssignmentId());
        wrapper.setScore(model.getScore());
        
        return wrapper;
    }

    public Grade toEntity(GradeWrapper wrapper) {
        if (wrapper == null) {
            return null;
        }
        
        Grade entity = new Grade();
        entity.setId(wrapper.getId());
        entity.setStudentId(wrapper.getStudentId());
        entity.setQuizId(wrapper.getQuizId());
        entity.setAssignmentId(wrapper.getAssignmentId());
        entity.setScore(wrapper.getScore());
        
        return entity;
    }

    @Override
    public List<GradeWrapper> findAll() {
        return gradeRepository.findAll()
                .stream()
                .map(this::toWrapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GradeWrapper> findById(Long id) {
        return gradeRepository.findById(id)
                .map(this::toWrapper);
    }

    @Override
    public GradeWrapper save(GradeWrapper gradeWrapper) {
        Grade entity = toEntity(gradeWrapper);
        Grade savedEntity = gradeRepository.save(entity);
        return toWrapper(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public List<GradeWrapper> findByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::toWrapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeWrapper> findByQuizId(Long quizId) {
        return gradeRepository.findByQuizId(quizId)
                .stream()
                .map(this::toWrapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeWrapper> findByAssignmentId(Long assignmentId) {
        return gradeRepository.findByAssignmentId(assignmentId)
                .stream()
                .map(this::toWrapper)
                .collect(Collectors.toList());
    }
}
