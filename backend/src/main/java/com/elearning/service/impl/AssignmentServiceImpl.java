package com.elearning.service.impl;

import com.elearning.model.Assignment;
import com.elearning.repository.AssignmentRepository;
import com.elearning.service.AssignmentService;
import com.elearning.wrapper.AssignmentWrapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public AssignmentWrapper toWrapper(Assignment model) {
        if (model == null) {
            return null;
        }
        
        AssignmentWrapper wrapper = new AssignmentWrapper();
        wrapper.setId(model.getId());
        wrapper.setTitle(model.getTitle());
        wrapper.setDescription(model.getDescription());
        wrapper.setDueDate(model.getDueDate());
        
        return wrapper;
    }

    public Assignment toEntity(AssignmentWrapper wrapper) {
        if (wrapper == null) {
            return null;
        }
        
        Assignment entity = new Assignment();
        entity.setId(wrapper.getId());
        entity.setTitle(wrapper.getTitle());
        entity.setDescription(wrapper.getDescription());
        entity.setDueDate(wrapper.getDueDate());
        
        return entity;
    }

    @Override
    public List<AssignmentWrapper> findAll() {
        return assignmentRepository.findAll()
                .stream()
                .map(this::toWrapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AssignmentWrapper> findById(Long id) {
        return assignmentRepository.findById(id)
                .map(this::toWrapper);
    }

    @Override
    public AssignmentWrapper save(AssignmentWrapper assignmentWrapper) {
        Assignment entity = toEntity(assignmentWrapper);
        Assignment savedEntity = assignmentRepository.save(entity);
        return toWrapper(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        assignmentRepository.deleteById(id);
    }
}
