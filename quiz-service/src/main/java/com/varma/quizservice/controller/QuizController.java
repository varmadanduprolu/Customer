package com.varma.quizservice.controller;

import com.varma.quizservice.model.QuestionWrapper;
import com.varma.quizservice.model.QuizDto;
import com.varma.quizservice.model.Response;
import com.varma.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * The QuizController class handles the HTTP requests related to quizzes.
 */
@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    /**
     * Creates a new quiz with the specified category, number of questions, and title.
     *
     * @param category       the category of the quiz
     * @param noOfQuestions  the number of questions in the quiz
     * @param title          the title of the quiz
     * @return a ResponseEntity with a success message and HTTP status code 201 (Created)
     */
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNoOfQuestions(), quizDto.getTitle());
    }

    /**
     * Retrieves the questions for a quiz with the specified ID.
     *
     * @param id the ID of the quiz
     * @return a ResponseEntity with a list of QuestionWrapper objects and HTTP status code 200 (OK)
     */
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }

    /**
     * Submits a quiz with the specified ID and user's responses.
     *
     * @param id         the ID of the quiz
     * @param responses  the list of user's responses
     * @return a ResponseEntity with the number of correct responses and HTTP status code 200 (OK)
     */
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResults(id, responses);
    }
}