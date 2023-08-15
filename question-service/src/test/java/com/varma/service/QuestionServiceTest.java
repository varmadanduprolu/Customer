package com.varma.service;

import com.varma.model.Question;
import com.varma.question.QuestionRequest;
import com.varma.questionDAO.QuestionDAO;
import com.varma.questionRepository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
        @InjectMocks
        private  QuestionService underTest;
        @Mock
        private QuestionRepository questionRepository;
        @Mock
        private QuestionDAO questionDAO;
        private  AutoCloseable autoCloseable;

        @Mock
        List<Question> questions;

//        @BeforeEach
//        void setUp() {
//            autoCloseable = MockitoAnnotations.openMocks(this);
//            underTest= new QuestionService(questionDAO,questionRepository);
//        }

//        @Test
//       void getAllQuestions() {
////            //given
////
////            //when
////            underTest.getAllQuestions();
////
////            //then
////            verify(questionRepository).findAll();
//        }
        @Test
        public void getAllQuestions(){
          //given
          List<Question> questions=new ArrayList<>();
          questions.add(new Question(1, "Question 1", "Option 1", "Option 2", "Option 3", "Option 4", "Easy", "Category 1", "Option 1"));
          questions.add(new Question(2, "Question 2", "Option 1", "Option 2", "Option 3", "Option 4", "Medium", "Category 2", "Option 2"));
        //when
               when(questionRepository.findAll()).thenReturn(questions);

                ResponseEntity<List<Question>> response=underTest.getAllQuestions();
                //then
               assertEquals(HttpStatus.OK,response.getStatusCode());
               assertEquals(questions,response.getBody());
        }

        @Test
        void getQuestionsBasedOnTheCategory() {
               //given
               String category= "Java";
                List<Question> questions=new ArrayList<>();
                questions.add(new Question(2, "Question 2", "Option 1", "Option 2", "Option 3", "Option 4", "Medium", category, "Option 2"));
               //when
               when(questionRepository.findQuestionByCategory(category)).thenReturn(questions);
                ResponseEntity<List<Question>> questionsBasedOnTheCategory = underTest.getQuestionsBasedOnTheCategory(category);
                //then
                assertEquals(HttpStatus.OK,questionsBasedOnTheCategory.getStatusCode());
                assertEquals(questions,questionsBasedOnTheCategory.getBody());
        }

        @Test
        void addQuestion() {
                //given
            QuestionRequest questionRequest = new QuestionRequest(
                    "What is the capital of France?",
                    "Paris",
                    "London",
                    "Berlin",
                    "Madrid",
                    "Easy",
                    "Geography",
                    "Paris"
            );
                //when
                when(questionRepository.existsByQuestion(questionRequest.question())).thenReturn(false);
                underTest.addQuestion(questionRequest);
                //then
                verify(questionRepository, times(1)).save(argThat(question ->
                        question.getQuestion().equals(questionRequest.question())&&
                        question.getOption1().equals(questionRequest.option1())&&
                        question.getOption3().equals(questionRequest.option3())&& question.getOption1().equals(questionRequest.option1())&&
                        question.getOption4().equals(questionRequest.option4())&&
                        question.getDifficultyLevel().equals(questionRequest.difficultyLevel())&&
                        question.getCategory().equals(questionRequest.category())&&
                        question.getCorrectAns().equals(questionRequest.correctAns())
                ));
        }

        @Test
        void getQuestionBasedOnTheDifficultyLevel() {
            // Arrange
            String difficultyLevel = "Easy";
            List<Question> expectedQuestions = new ArrayList<>();
            expectedQuestions.add(new Question(1, "Question 1", "Option 1", "Option 2", "Option 3", "Option 4", "Easy", "Category 1", "Option 1"));
            expectedQuestions.add(new Question(2, "Question 2", "Option 1", "Option 2", "Option 3", "Option 4", "Easy", "Category 2", "Option 2"));

            // Mock the behavior of the questionRepository
            when(questionRepository.findQuestionByDifficultyLevel(difficultyLevel)).thenReturn(expectedQuestions);

            // Act
            ResponseEntity<List<Question>> response = underTest.getQuestionBasedOnTheDifficultyLevel(difficultyLevel);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedQuestions, response.getBody());
        }

        @Test
        void getQuestionsBasedOnTheCategoryAndDifficultyLevel() {
        }

        @Test
        void deleteQuestionById() {
        }

        @Test
        void updateQuestion() {
        }

        @Test
        void findRandomQuestionsByCategory() {
        }

        @Test
        void getQuestionsForQuiz() {
        }

        @Test
        void getQuestionsFromId() {
        }

        @Test
        void getScore() {
        }
}