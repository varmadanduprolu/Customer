package com.varma.quizservice.quiz;
/**
 * The QuestionUpdate record represents the updated details of a question.
 */
public record QuestionUpdate(String question, String option1, String option2, String option3, String option4, String difficultyLevel, String category, String correctAns) {
}