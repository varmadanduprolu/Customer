package com.varma.questionRepository;

import com.varma.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The QuestionRepository interface provides methods for querying the question entities in the database.
 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    /**
     * Finds questions by category.
     *
     * @param category the category of the questions
     * @return a list of questions matching the category
     */
    List<Question> findQuestionByCategory(String category);

    /**
     * Finds questions by difficulty level.
     *
     * @param difficultyLevel the difficulty level of the questions
     * @return a list of questions matching the difficulty level
     */
    List<Question> findQuestionByDifficultyLevel(String difficultyLevel);

    /**
     * Finds questions by category and difficulty level.
     *
     * @param category        the category of the questions
     * @param difficultyLevel the difficulty level of the questions
     * @return a list of questions matching the category and difficulty level
     */
    List<Question> findQuestionByCategoryAndDifficultyLevel(String category, String difficultyLevel);

    /**
     * Finds questions by category before a certain difficulty level.
     *
     * @param category        the category of the questions
     * @param difficultyLevel the difficulty level of the questions
     * @return a list of questions matching the category and difficulty level
     */
    List<Question> findQuestionByCategoryBeforeAndDifficultyLevel(String category, String difficultyLevel);

    /**
     * Finds random questions by category.
     *
     * @param category      the category of the questions
     * @param noOfQuestions the number of random questions to retrieve
     * @return a list of random questions matching the category
     */


    /**
     * Checks if a question exists by its text.
     *
     * @param question the text of the question
     * @return true if the question exists, false otherwise
     */
    boolean existsByQuestion(String question);
    @Query(value = "SELECT q.id FROM question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :noOfQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer noOfQuestions);
}
