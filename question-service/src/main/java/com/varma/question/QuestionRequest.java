package com.varma.question;

/**
 * The QuestionRequest record represents the details of a question request.
 */
public record QuestionRequest(String question, String option1, String option2, String option3, String option4, String difficultyLevel, String category, String correctAns) {
}
