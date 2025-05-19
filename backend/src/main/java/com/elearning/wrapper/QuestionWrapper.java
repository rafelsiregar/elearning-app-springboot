package com.elearning.wrapper;

import com.elearning.model.QuestionType;
import java.util.List;
import java.util.Map;

public class QuestionWrapper {
    private Long id;
    private String text;
    private QuestionType type;
    private List<String> options;
    private Integer correctOptionIndex;
    private String correctAnswer;
    private Map<String, String> matching;
    private String rubric;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Integer getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(Integer correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Map<String, String> getMatching() {
        return matching;
    }

    public void setMatching(Map<String, String> matching) {
        this.matching = matching;
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }
}
