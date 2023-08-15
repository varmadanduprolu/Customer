package com.varma.questionDAO;

import com.varma.model.Question;
import com.varma.model.QuestionWrapper;
import com.varma.model.Response;
import com.varma.question.QuestionRequest;
import com.varma.question.QuestionUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The QuestionDAO interface provides methods for managing questions in the quiz application.
 */
public interface QuestionDAO {

    /**
     * Retrieves all questions.
     *
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    ResponseEntity<List<Question>> getAllQuestions();

    /**
     * Retrieves questions based on the specified category.
     *
     * @param category the category of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    ResponseEntity<List<Question>> getQuestionsBasedOnTheCategory(String category);

    /**
     * Adds a new question.
     *
     * @param questionRequest the question request object containing the question details
     */
    void addQuestion(QuestionRequest questionRequest);

    /**
     * Retrieves questions based on the specified difficulty level.
     *
     * @param difficultyLevel the difficulty level of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    ResponseEntity<List<Question>> getQuestionBasedOnTheDifficultyLevel(String difficultyLevel);

    /**
     * Retrieves questions based on the specified category and difficulty level.
     *
     * @param category        the category of the questions
     * @param difficultyLevel the difficulty level of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    ResponseEntity<List<Question>> getQuestionsBasedOnTheCategoryAndDifficultyLevel(String category, String difficultyLevel);

    /**
     * Deletes a question by its ID.
     *
     * @param id the ID of the question to be deleted
     */
    void deleteQuestionById(Integer id);

    /**
     * Updates a question with the specified ID.
     *
     * @param id             the ID of the question to be updated
     * @param questionUpdate the updated question object
     * @throws Exception if an error occurs during the update process
     */
    void updateQuestion(Integer id, QuestionUpdate questionUpdate) throws Exception;

    /**
     * Finds random questions based on the specified category and number of questions.
     *
     * @param categoryName      the category of the questions
     * @param noOfQuestions the number of random questions to retrieve
     * @return a list of random questions matching the category
     */

    ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer noOfQuestions);
    List<Integer> findRandomQuestionsByCategory(String category, Integer noOfQuestions);

    ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds);

    ResponseEntity<Integer> getScore(List<Response> responses);
}