package com.varma.quizservice.service;


import com.varma.quizservice.exception.ResourceNotFoundException;
import com.varma.quizservice.feign.QuizInterface;
import com.varma.quizservice.model.QuestionWrapper;
import com.varma.quizservice.model.Quiz;
import com.varma.quizservice.model.Response;
import com.varma.quizservice.quizDAO.QuizDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * The QuizService class provides methods for creating quizzes, retrieving quiz questions, and calculating quiz results.
 */
@Service
public class QuizService {

    @Autowired
    private QuizDAO quizDAO;

    @Autowired
    private QuizInterface quizInterface;

    /**
     * Creates a new quiz with the specified category, number of questions, and title.8888888888888888888888888888888888888888888888888888888888888888888888
     * @param category       the category of the quiz
     * @param noOfQuestions  the number of questions in the quiz
     * @param title          the title of the quiz
     * @return a ResponseEntity with a success message and HTTP status code 201 (Created)
     */
    public ResponseEntity<String> createQuiz(String category, Integer noOfQuestions, String title) {

        List<Integer> questions= quizInterface.getQuestionsForQuiz(category,noOfQuestions).getBody();

        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    /**
     * Retrieves the questions for a quiz with the specified ID.
     *
     * @param id the ID of the quiz
     * @return a ResponseEntity with a list of QuestionWrapper objects and HTTP status code 200 (OK)
     * @throws ResourceNotFoundException if the quiz with the specified ID is not found
     */
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        if (!quizDAO.existsQuizById(id)) {
            throw new ResourceNotFoundException("Quiz with id %d not found".formatted(id));
        }
        Quiz quiz = quizDAO.findById(id).get();
        List<Integer> questionIds=quiz.getQuestions();

        ResponseEntity<List<QuestionWrapper>> questionsFromId= quizInterface.getQuestionsFromId(questionIds);
        return questionsFromId;
    }

    /**
     * Calculates the results for a quiz with the specified ID based on the user's responses.
     *
     * @param id         the ID of the quiz
     * @param responses  the list of user's responses
     * @return a ResponseEntity with the number of correct responses and HTTP status code 200 (OK)
     * @throws ResourceNotFoundException if the quiz with the specified ID is not found
     */
    public ResponseEntity<Integer> calculateResults(Integer id, List<Response> responses) {

        if (!quizDAO.existsQuizById(id)) {
            throw new ResourceNotFoundException("Quiz with id %d not found".formatted(id));
        }

       ResponseEntity<Integer> result= quizInterface.getScore(responses);

        return result;
    }
}

