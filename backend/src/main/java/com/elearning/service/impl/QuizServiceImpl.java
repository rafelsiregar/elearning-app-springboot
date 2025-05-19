package com.elearning.service.impl;

import com.elearning.model.Quiz;
import com.elearning.model.Question;
import com.elearning.repository.QuizRepository;
import com.elearning.service.QuizService;
import com.elearning.wrapper.QuizWrapper;
import com.elearning.wrapper.QuestionWrapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuestionWrapper toQuestionWrapper(Question model) {
        if (model == null) {
            return null;
        }
        
        QuestionWrapper wrapper = new QuestionWrapper();
        wrapper.setId(model.getId());
        wrapper.setText(model.getText());
        wrapper.setType(model.getType());
        wrapper.setOptions(model.getOptions());
        wrapper.setCorrectOptionIndex(model.getCorrectOptionIndex());
        wrapper.setCorrectAnswer(model.getCorrectAnswer());
        wrapper.setMatching(model.getMatching());
        wrapper.setRubric(model.getRubric());
        
        return wrapper;
    }

    public Question toQuestionEntity(QuestionWrapper wrapper) {
        if (wrapper == null) {
            return null;
        }
        
        Question entity = new Question();
        entity.setId(wrapper.getId());
        entity.setText(wrapper.getText());
        entity.setType(wrapper.getType());
        entity.setOptions(wrapper.getOptions());
        entity.setCorrectOptionIndex(wrapper.getCorrectOptionIndex());
        entity.setCorrectAnswer(wrapper.getCorrectAnswer());
        entity.setMatching(wrapper.getMatching());
        entity.setRubric(wrapper.getRubric());
        
        return entity;
    }

    public QuizWrapper toWrapper(Quiz model) {
        if (model == null) {
            return null;
        }
        
        QuizWrapper wrapper = new QuizWrapper();
        wrapper.setId(model.getId());
        wrapper.setTitle(model.getTitle());
        
        if (model.getQuestions() != null) {
            List<QuestionWrapper> questionWrappers = model.getQuestions()
                .stream()
                .map(this::toQuestionWrapper)
                .collect(Collectors.toList());
            wrapper.setQuestions(questionWrappers);
        }
        
        return wrapper;
    }

    public Quiz toEntity(QuizWrapper wrapper) {
        if (wrapper == null) {
            return null;
        }
        
        Quiz entity = new Quiz();
        entity.setId(wrapper.getId());
        entity.setTitle(wrapper.getTitle());
        
        if (wrapper.getQuestions() != null) {
            List<Question> questions = wrapper.getQuestions()
                .stream()
                .map(this::toQuestionEntity)
                .collect(Collectors.toList());
            entity.setQuestions(questions);
        }
        
        return entity;
    }

    @Override
    public List<QuizWrapper> findAll() {
        return quizRepository.findAll()
                .stream()
                .map(this::toWrapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<QuizWrapper> findById(Long id) {
        return quizRepository.findById(id)
                .map(this::toWrapper);
    }

    @Override
    public QuizWrapper save(QuizWrapper quizWrapper) {
        Quiz entity = toEntity(quizWrapper);
        Quiz savedEntity = quizRepository.save(entity);
        return toWrapper(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
