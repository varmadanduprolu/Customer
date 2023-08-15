package com.varma.model;

import lombok.NoArgsConstructor;

/**
 * The QuestionWrapper record represents a wrapper object for a question.
 */

public record QuestionWrapper(Integer id, String question, String option1, String option2, String option3, String option4) {

}