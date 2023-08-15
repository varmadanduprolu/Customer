package com.varma.quizservice.model;


import lombok.Data;

@Data
public class QuizDto {
    String category;
    Integer noOfQuestions;
    String title;
}
