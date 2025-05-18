package com.elearning.controller;

import com.elearning.dto.QuestionDTO;
import com.elearning.dto.QuizDTO;
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

    private QuizDTO toDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        List<QuestionDTO> questionDTOs = quiz.getQuestions().stream().map(this::toDTO).collect(Collectors.toList());
        dto.setQuestions(questionDTOs);
        return dto;
    }

    private QuestionDTO toDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        dto.setOptions(question.getOptions());
        dto.setCorrectOptionIndex(question.getCorrectOptionIndex());
        return dto;
    }

    private Quiz toEntity(QuizDTO dto) {
        Quiz quiz = new Quiz();
        quiz.setId(dto.getId());
        quiz.setTitle(dto.getTitle());
        List<Question> questions = dto.getQuestions().stream().map(this::toEntity).collect(Collectors.toList());
        quiz.setQuestions(questions);
        return quiz;
    }

    private Question toEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setId(dto.getId());
        question.setText(dto.getText());
        question.setOptions(dto.getOptions());
        question.setCorrectOptionIndex(dto.getCorrectOptionIndex());
        return question;
    }

    @GetMapping
    public List<QuizDTO> getAllQuizzes() {
        return quizService.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.findById(id);
        return quiz.map(q -> ResponseEntity.ok(toDTO(q))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizDTO createQuiz(@RequestBody QuizDTO quizDTO) {
        Quiz quiz = toEntity(quizDTO);
        Quiz savedQuiz = quizService.save(quiz);
        return toDTO(savedQuiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable Long id, @RequestBody QuizDTO quizDTO) {
        Optional<Quiz> quizOptional = quizService.findById(id);
        if (!quizOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quiz = quizOptional.get();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setQuestions(quizDTO.getQuestions().stream().map(this::toEntity).collect(Collectors.toList()));
        Quiz updatedQuiz = quizService.save(quiz);
        return ResponseEntity.ok(toDTO(updatedQuiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
