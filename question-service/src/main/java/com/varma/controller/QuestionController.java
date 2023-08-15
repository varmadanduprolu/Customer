package com.varma.controller;

import com.varma.model.QuestionWrapper;
import com.varma.model.Response;
import com.varma.question.QuestionRequest;
import com.varma.question.QuestionUpdate;
import com.varma.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.varma.model.Question;
import java.util.List;


/**
 * The QuestionController class handles the HTTP requests related to questions.
 */
@RestController
@RequestMapping("api/v1")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * Retrieves all questions.
     *
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    /**
     * Retrieves questions based on the specified category.
     *
     * @param category the category of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsBasedOnCategory(@PathVariable String category) {
        return questionService.getQuestionsBasedOnTheCategory(category);
    }

    /**
     * Retrieves questions based on the specified difficulty level.
     *
     * @param difficultyLevel the difficulty level of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @GetMapping("difficultyLevel/{difficultyLevel}")
    public ResponseEntity<List<Question>> getQuestionBasedOnTheDifficultyLevel(@PathVariable String difficultyLevel) {
        return questionService.getQuestionBasedOnTheDifficultyLevel(difficultyLevel);
    }

    /**
     * Retrieves questions based on the specified category and difficulty level.
     *
     * @param category        the category of the questions
     * @param difficultyLevel the difficulty level of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @GetMapping("category/{category}/difficultyLevel/{difficultyLevel}")
    public ResponseEntity<List<Question>> getQuestionsBasedOnTheCategoryAndDifficultyLevel(@PathVariable String category, @PathVariable String difficultyLevel) {
        return questionService.getQuestionsBasedOnTheCategoryAndDifficultyLevel(category, difficultyLevel);
    }

    /**
     * Adds a new question.
     *
     * @param questionRequest the question request object containing the question details
     */
    @PostMapping("addNewQuestion")
    public void addQuestions(@RequestBody QuestionRequest questionRequest) {
        questionService.addQuestion(questionRequest);
    }

    /**
     * Deletes a question by its ID.
     *
     * @param id the ID of the question to be deleted
     */
    @DeleteMapping("deleteQuestion/{id}")
    public void deleteQuestionById(@PathVariable Integer id) {
        questionService.deleteQuestionById(id);
    }

    /**
     * Updates a question with the specified ID.
     *
     * @param id             the ID of the question to be updated
     * @param questionUpdate the updated question object
     * @throws Exception if no changes are found or if the question with the specified ID is not found
     */
    @PutMapping("updateQuestion/{id}")
    public void updateQuestionById(@PathVariable Integer id, @RequestBody QuestionUpdate questionUpdate) throws Exception {
        questionService.updateQuestion(id, questionUpdate);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz( String category, Integer noOfQuestions){
        return questionService.getQuestionsForQuiz(category,noOfQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsIds){
        return questionService.getQuestionsFromId(questionsIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
    // generate
    // getQuestions (questionId)
    // getScore
}