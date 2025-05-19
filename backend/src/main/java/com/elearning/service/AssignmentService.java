package com.elearning.service;

import com.elearning.model.Assignment;
import com.elearning.wrapper.AssignmentWrapper;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    List<AssignmentWrapper> findAll();
    
    Optional<AssignmentWrapper> findById(Long id);
    
    AssignmentWrapper save(AssignmentWrapper assignmentWrapper);
    
    void deleteById(Long id);
}
