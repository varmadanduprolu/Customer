package com.varma.quizservice.service;

import com.varma.quizservice.exception.ResourceNotFoundException;
import com.varma.quizservice.feign.QuizInterface;
import com.varma.quizservice.model.QuestionWrapper;
import com.varma.quizservice.model.Quiz;
import com.varma.quizservice.quizDAO.QuizDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizDAO quizDAO;

    @Mock
    private QuizInterface quizInterface;

    @InjectMocks
    private QuizService underTest;


    @Test
    @DisplayName("Should throw a ResourceNotFoundException when the quiz id does not exist")
    void getQuizQuestionsWhenQuizIdDoesNotExistThenThrowException() {
        int quizId = 1;
        when(quizDAO.existsQuizById(quizId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            underTest.getQuizQuestions(quizId);
        });

        assertThat(exception.getMessage()).isEqualTo("Quiz with id 1 not found");

        verify(quizDAO, times(1)).existsQuizById(quizId);
        verifyNoMoreInteractions(quizDAO, quizInterface);
    }

    @Test
    @DisplayName("Should return the list of questions when the quiz id exists")
    void getQuizQuestionsWhenQuizIdExists() {
        Integer quizId = 1;
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        List<Integer> questionIds = new ArrayList<>();
        questionIds.add(1);
        questionIds.add(2);
        quiz.setQuestions(questionIds);

        List<QuestionWrapper> expectedQuestions = new ArrayList<>();
        expectedQuestions.add(new QuestionWrapper(1, "Question 1", "Option 1", "Option 2", "Option 3", "Option 4"));
        expectedQuestions.add(new QuestionWrapper(2, "Question 2", "Option 1", "Option 2", "Option 3", "Option 4"));

        when(quizDAO.existsQuizById(quizId)).thenReturn(true);
        when(quizDAO.findById(quizId)).thenReturn(Optional.of(quiz));
        when(quizInterface.getQuestionsFromId(questionIds)).thenReturn(ResponseEntity.ok(expectedQuestions));

        ResponseEntity<List<QuestionWrapper>> result = underTest.getQuizQuestions(quizId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedQuestions);

        verify(quizDAO, times(1)).existsQuizById(quizId);
        verify(quizDAO, times(1)).findById(quizId);
        verify(quizInterface, times(1)).getQuestionsFromId(questionIds);
    }

    @Test
    @DisplayName("Should save the created quiz in the database")
    void createQuizShouldSaveInDatabase() {
        String category = "History";
        Integer noOfQuestions = 10;
        String title = "History Quiz";

        List<Integer> questions = new ArrayList<>();
        questions.add(1);
        questions.add(2);
        questions.add(3);

        ResponseEntity<List<Integer>> questionsResponse = ResponseEntity.ok(questions);
        when(quizInterface.getQuestionsForQuiz(category, noOfQuestions)).thenReturn(questionsResponse);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        when(quizDAO.save(any(Quiz.class))).thenReturn(quiz);

        ResponseEntity<String> response = underTest.createQuiz(category, noOfQuestions, title);

//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("success", response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("success");
        verify(quizInterface, times(1)).getQuestionsForQuiz(category, noOfQuestions);
        verify(quizDAO, times(1)).save(any(Quiz.class));
    }

//    @Test
//    void getQuizQuestionsByExistingId() {
//        Integer id = 1;
//        when(quizDAO.existsQuizById(id)).thenReturn(true);
//        Quiz quiz = new Quiz();
//        when(quizDAO.findById(id)).thenReturn(Optional.of(quiz));
//        List<Integer> questionIds = quiz.getQuestions();
//
//
//        ResponseEntity<List<QuestionWrapper>> questionsResponse = ResponseEntity.ok(new ArrayList<>());
//        when(quizInterface.getQuestionsFromId(questionIds)).thenReturn(questionsResponse);
//
//        ResponseEntity<List<QuestionWrapper>> response = underTest.getQuizQuestions(id);
//        assertThat(response.getStatusCode()).isEqualTo(200);
//        assertThat(response.getBody()).isEqualTo(questionsResponse);
//
//
//    }

    @Test
    void calculateResults() {
    }
}