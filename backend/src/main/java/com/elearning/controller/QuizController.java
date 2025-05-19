package com.elearning.controller;

import com.elearning.wrapper.QuestionWrapper;
import com.elearning.wrapper.QuizWrapper;
import com.elearning.model.Question;
import com.elearning.model.Quiz;
import com.elearning.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    private QuizWrapper toWrapper(Quiz quiz) {
        QuizWrapper wrapper = new QuizWrapper();
        wrapper.setId(quiz.getId());
        wrapper.setTitle(quiz.getTitle());
        List<QuestionWrapper> questionWrappers = quiz.getQuestions().stream()
            .map(this::toWrapper)
            .collect(Collectors.toList());
        wrapper.setQuestions(questionWrappers);
        return wrapper;
    }

    private QuestionWrapper toWrapper(Question question) {
        QuestionWrapper wrapper = new QuestionWrapper();
        wrapper.setId(question.getId());
        wrapper.setText(question.getText());
        wrapper.setType(question.getType());
        wrapper.setOptions(question.getOptions());
        wrapper.setCorrectOptionIndex(question.getCorrectOptionIndex());
        wrapper.setCorrectAnswer(question.getCorrectAnswer());
        wrapper.setMatching(question.getMatching());
        wrapper.setRubric(question.getRubric());
        return wrapper;
    }

    private Quiz toEntity(QuizWrapper wrapper) {
        Quiz quiz = new Quiz();
        quiz.setId(wrapper.getId());
        quiz.setTitle(wrapper.getTitle());
        List<Question> questions = wrapper.getQuestions().stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
        quiz.setQuestions(questions);
        return quiz;
    }

    private Question toEntity(QuestionWrapper wrapper) {
        Question question = new Question();
        question.setId(wrapper.getId());
        question.setText(wrapper.getText());
        question.setType(wrapper.getType());
        question.setOptions(wrapper.getOptions());
        question.setCorrectOptionIndex(wrapper.getCorrectOptionIndex());
        question.setCorrectAnswer(wrapper.getCorrectAnswer());
        question.setMatching(wrapper.getMatching());
        question.setRubric(wrapper.getRubric());
        return question;
    }

    @GetMapping
    public List<QuizWrapper> getAllQuizzes() {
        return quizService.findAll().stream()
            .map(this::toWrapper)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizWrapper> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.findById(id);
        return quiz.map(q -> ResponseEntity.ok(toWrapper(q)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizWrapper createQuiz(@RequestBody QuizWrapper quizWrapper) {
        Quiz quiz = toEntity(quizWrapper);
        Quiz savedQuiz = quizService.save(quiz);
        return toWrapper(savedQuiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizWrapper> updateQuiz(
            @PathVariable Long id,
            @RequestBody QuizWrapper quizWrapper) {
        Optional<Quiz> quizOptional = quizService.findById(id);
        if (!quizOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quiz = quizOptional.get();
        quiz.setTitle(quizWrapper.getTitle());
        quiz.setQuestions(quizWrapper.getQuestions().stream()
            .map(this::toEntity)
            .collect(Collectors.toList()));
        Quiz updatedQuiz = quizService.save(quiz);
        return ResponseEntity.ok(toWrapper(updatedQuiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
