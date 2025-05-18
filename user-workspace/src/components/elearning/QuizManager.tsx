"use client";

import React, { useState, useEffect } from "react";

type QuestionType = 'MULTIPLE_CHOICE' | 'TRUE_FALSE' | 'SHORT_ANSWER' | 'ESSAY' | 'MATCHING';

interface Question {
  id?: number;
  text: string;
  type: QuestionType;
  options: string[];
  correctOptionIndex?: number;
  correctAnswer?: string;
  matching?: { [key: string]: string };
  rubric?: string;
}

interface Quiz {
  id?: number;
  title: string;
  questions: Question[];
}

export default function QuizManager() {
  const [quizzes, setQuizzes] = useState<Quiz[]>([]);
  const [title, setTitle] = useState("");
  const [questions, setQuestions] = useState<Question[]>([]);
  const [questionText, setQuestionText] = useState("");
  const [questionType, setQuestionType] = useState<QuestionType>("MULTIPLE_CHOICE");
  const [options, setOptions] = useState<string[]>(["", "", "", ""]);
  const [correctOptionIndex, setCorrectOptionIndex] = useState<number>(0);
  const [correctAnswer, setCorrectAnswer] = useState("");
  const [matchingPairs, setMatchingPairs] = useState<{ key: string; value: string }[]>([{ key: "", value: "" }]);
  const [rubric, setRubric] = useState("");

  useEffect(() => {
    fetch("/api/quizzes")
      .then((res) => res.json())
      .then(setQuizzes)
      .catch(console.error);
  }, []);

  const addQuestion = () => {
    if (!questionText.trim()) return;

    const newQuestion: Question = {
      text: questionText,
      type: questionType,
      options: [],
    };

    switch (questionType) {
      case "MULTIPLE_CHOICE":
        newQuestion.options = options.filter((opt) => opt.trim() !== "");
        newQuestion.correctOptionIndex = correctOptionIndex;
        break;
      case "TRUE_FALSE":
        newQuestion.options = ["True", "False"];
        newQuestion.correctOptionIndex = correctOptionIndex;
        break;
      case "SHORT_ANSWER":
      case "ESSAY":
        newQuestion.correctAnswer = correctAnswer;
        newQuestion.rubric = rubric;
        break;
      case "MATCHING":
        newQuestion.matching = matchingPairs.reduce((acc, pair) => {
          if (pair.key.trim() && pair.value.trim()) {
            acc[pair.key] = pair.value;
          }
          return acc;
        }, {} as { [key: string]: string });
        break;
    }

    setQuestions([...questions, newQuestion]);
    resetForm();
  };

  const resetForm = () => {
    setQuestionText("");
    setQuestionType("MULTIPLE_CHOICE");
    setOptions(["", "", "", ""]);
    setCorrectOptionIndex(0);
    setCorrectAnswer("");
    setMatchingPairs([{ key: "", value: "" }]);
    setRubric("");
  };

  const submitQuiz = () => {
    if (!title.trim() || questions.length === 0) return;
    
    const newQuiz: Quiz = { title, questions };
    fetch("/api/quizzes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newQuiz),
    })
      .then((res) => res.json())
      .then((createdQuiz) => {
        setQuizzes([...quizzes, createdQuiz]);
        setTitle("");
        setQuestions([]);
      })
      .catch(console.error);
  };

  const addMatchingPair = () => {
    setMatchingPairs([...matchingPairs, { key: "", value: "" }]);
  };

  return (
    <div className="max-w-4xl mx-auto">
      <h2 className="text-2xl font-semibold mb-4">Quizzes</h2>

      <div className="mb-6 space-y-4">
        <input
          type="text"
          placeholder="Quiz Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="w-full p-2 border rounded"
        />

        <div className="space-y-4">
          <input
            type="text"
            placeholder="Question Text"
            value={questionText}
            onChange={(e) => setQuestionText(e.target.value)}
            className="w-full p-2 border rounded"
          />

          <select
            value={questionType}
            onChange={(e) => setQuestionType(e.target.value as QuestionType)}
            className="w-full p-2 border rounded"
          >
            <option value="MULTIPLE_CHOICE">Multiple Choice</option>
            <option value="TRUE_FALSE">True/False</option>
            <option value="SHORT_ANSWER">Short Answer</option>
            <option value="ESSAY">Essay</option>
            <option value="MATCHING">Matching</option>
          </select>

          {questionType === "MULTIPLE_CHOICE" && (
            <div className="space-y-2">
              {options.map((opt, idx) => (
                <div key={idx} className="flex items-center space-x-2">
                  <input
                    type="radio"
                    name="correctOption"
                    checked={correctOptionIndex === idx}
                    onChange={() => setCorrectOptionIndex(idx)}
                  />
                  <input
                    type="text"
                    placeholder={`Option ${idx + 1}`}
                    value={opt}
                    onChange={(e) => {
                      const newOptions = [...options];
                      newOptions[idx] = e.target.value;
                      setOptions(newOptions);
                    }}
                    className="flex-1 p-2 border rounded"
                  />
                </div>
              ))}
            </div>
          )}

          {questionType === "TRUE_FALSE" && (
            <div className="space-y-2">
              <div className="flex items-center space-x-2">
                <input
                  type="radio"
                  name="trueFalse"
                  checked={correctOptionIndex === 0}
                  onChange={() => setCorrectOptionIndex(0)}
                />
                <span>True</span>
              </div>
              <div className="flex items-center space-x-2">
                <input
                  type="radio"
                  name="trueFalse"
                  checked={correctOptionIndex === 1}
                  onChange={() => setCorrectOptionIndex(1)}
                />
                <span>False</span>
              </div>
            </div>
          )}

          {(questionType === "SHORT_ANSWER" || questionType === "ESSAY") && (
            <div className="space-y-2">
              <input
                type="text"
                placeholder="Correct Answer"
                value={correctAnswer}
                onChange={(e) => setCorrectAnswer(e.target.value)}
                className="w-full p-2 border rounded"
              />
              <textarea
                placeholder="Rubric/Grading Guidelines"
                value={rubric}
                onChange={(e) => setRubric(e.target.value)}
                className="w-full p-2 border rounded"
                rows={3}
              />
            </div>
          )}

          {questionType === "MATCHING" && (
            <div className="space-y-2">
              {matchingPairs.map((pair, idx) => (
                <div key={idx} className="flex space-x-2">
                  <input
                    type="text"
                    placeholder="Item"
                    value={pair.key}
                    onChange={(e) => {
                      const newPairs = [...matchingPairs];
                      newPairs[idx].key = e.target.value;
                      setMatchingPairs(newPairs);
                    }}
                    className="flex-1 p-2 border rounded"
                  />
                  <input
                    type="text"
                    placeholder="Match"
                    value={pair.value}
                    onChange={(e) => {
                      const newPairs = [...matchingPairs];
                      newPairs[idx].value = e.target.value;
                      setMatchingPairs(newPairs);
                    }}
                    className="flex-1 p-2 border rounded"
                  />
                </div>
              ))}
              <button
                onClick={addMatchingPair}
                className="w-full p-2 bg-gray-100 text-black rounded hover:bg-gray-200"
              >
                Add Matching Pair
              </button>
            </div>
          )}

          <button
            onClick={addQuestion}
            className="w-full p-2 bg-black text-white rounded hover:bg-gray-800"
          >
            Add Question
          </button>
        </div>

        <button
          onClick={submitQuiz}
          className="w-full p-2 bg-black text-white rounded hover:bg-gray-800"
        >
          Create Quiz
        </button>
      </div>

      <div className="space-y-4">
        <h3 className="text-xl font-semibold">Existing Quizzes</h3>
        {quizzes.length === 0 && <p>No quizzes available.</p>}
        {quizzes.map((quiz) => (
          <div key={quiz.id} className="border rounded p-4">
            <h4 className="font-semibold text-lg">{quiz.title}</h4>
            <ul className="mt-2 space-y-2">
              {quiz.questions.map((q, i) => (
                <li key={i} className="ml-4">
                  <span className="font-medium">Q{i + 1}:</span> {q.text}
                  <span className="text-gray-500 ml-2">({q.type})</span>
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}
