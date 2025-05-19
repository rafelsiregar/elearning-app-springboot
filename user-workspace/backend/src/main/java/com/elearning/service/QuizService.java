package com.elearning.service;

import com.elearning.wrapper.QuizWrapper;
import java.util.List;
import java.util.Optional;

public interface QuizService {
    List<QuizWrapper> findAll();
    
    Optional<QuizWrapper> findById(Long id);
    
    QuizWrapper save(QuizWrapper quizWrapper);
    
    void deleteById(Long id);
}
