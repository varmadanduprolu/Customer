package com.varma.quizservice.quizDAO;


import com.varma.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The QuizDAO interface extends the JpaRepository interface to provide CRUD operations for the Quiz entity.
 */
public interface QuizDAO extends JpaRepository<Quiz, Integer> {

    /**
     * Checks if a quiz exists by its ID.
     *
     * @param id the ID of the quiz
     * @return true if the quiz exists, false otherwise
     */
    boolean existsQuizById(Integer id);
}