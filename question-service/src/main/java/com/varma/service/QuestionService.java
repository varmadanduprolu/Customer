package com.varma.service;


import com.varma.exception.DuplicateException;
import com.varma.exception.ResourceNotFoundException;
import com.varma.model.Question;
import com.varma.model.QuestionWrapper;
import com.varma.model.Response;
import com.varma.question.QuestionRequest;
import com.varma.question.QuestionUpdate;
import com.varma.questionDAO.QuestionDAO;
import com.varma.questionRepository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The QuestionService class provides methods for managing questions in the quiz application.
 */
@Service
public class QuestionService implements QuestionDAO {

    @Autowired
    private QuestionRepository questionRepository;

//    public QuestionService(QuestionDAO questionDAO, QuestionRepository questionRepository) {
//    }


    /**
     * Retrieves all questions.
     *
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Retrieves questions based on the specified category.
     *
     * @param category the category of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @Override
    public ResponseEntity<List<Question>> getQuestionsBasedOnTheCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findQuestionByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Adds a new question.
     *
     * @param questionRequest the question request object containing the question details
     * @throws DuplicateException if the question already exists
     */
    public void addQuestion(QuestionRequest questionRequest) {
        String question1 = questionRequest.question();
        if (question1 == null || questionRepository.existsByQuestion(question1)) {
            throw new DuplicateException("Question already exists");
        }
        Question question = new Question(
                question1,
                questionRequest.option1(),
                questionRequest.option2(),
                questionRequest.option3(),
                questionRequest.option4(),
                questionRequest.difficultyLevel(),
                questionRequest.category(),
                questionRequest.correctAns()
        );
        questionRepository.save(question);
    }

    /**
     * Retrieves questions based on the specified difficulty level.
     *
     * @param difficultyLevel the difficulty level of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @Override
    public ResponseEntity<List<Question>> getQuestionBasedOnTheDifficultyLevel(String difficultyLevel) {
        try {
            return new ResponseEntity<>(questionRepository.findQuestionByDifficultyLevel(difficultyLevel), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Retrieves questions based on the specified category and difficulty level.
     *
     * @param category        the category of the questions
     * @param difficultyLevel the difficulty level of the questions
     * @return a ResponseEntity with the list of questions and HTTP status code 200 (OK)
     */
    @Override
    public ResponseEntity<List<Question>> getQuestionsBasedOnTheCategoryAndDifficultyLevel(String category, String difficultyLevel) {
        try {
            return new ResponseEntity<>(questionRepository.findQuestionByCategoryAndDifficultyLevel(category, difficultyLevel), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes a question by its ID.
     *
     * @param id the ID of the question to be deleted
     * @throws ResourceNotFoundException if the question with the specified ID is not found
     */
    @Override
    public void deleteQuestionById(Integer id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with ID: " + id);
        }
        questionRepository.deleteById(id);
    }

    /**
     * Updates a question with the specified ID.
     *
     * @param id             the ID of the question to be updated
     * @param questionUpdate the updated question object
     * @throws Exception if no changes are found or if the question with the specified ID is not found
     */
    @Override
    public void updateQuestion(Integer id, QuestionUpdate questionUpdate) throws Exception {
        Optional<Question> question = Optional.of(questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id)));
        boolean change = false;
        if (questionUpdate.question() != null && !question.get().getQuestion().equals(questionUpdate.question())) {
            question.get().setQuestion(questionUpdate.question());
            change = true;
        }
        if (questionUpdate.option1() != null && !question.get().getOption1().equals(questionUpdate.option1())) {
            question.get().setOption1(questionUpdate.option1());
            change = true;
        }
        if (questionUpdate.option2() != null && !question.get().getOption2().equals(questionUpdate.option2())) {
            question.get().setOption2(questionUpdate.option2());
            change = true;
        }
        if (questionUpdate.option3() != null && !question.get().getOption3().equals(questionUpdate.option3())) {
            question.get().setOption3(questionUpdate.option3());
            change = true;
        }
        if (questionUpdate.option4() != null && !question.get().getOption4().equals(questionUpdate.option4())) {
            question.get().setOption4(questionUpdate.option4());
            change = true;
        }
        if (questionUpdate.category() != null && !question.get().getCategory().equals(questionUpdate.category())) {
            question.get().setCategory(questionUpdate.category());
            change = true;
        }
        if (questionUpdate.difficultyLevel() != null && !question.get().getDifficultyLevel().equals(questionUpdate.difficultyLevel())) {
            question.get().setDifficultyLevel(questionUpdate.difficultyLevel());
            change = true;
        }
        if (questionUpdate.correctAns() != null && !question.get().getCorrectAns().equals(questionUpdate.correctAns())) {
            question.get().setCorrectAns(questionUpdate.correctAns());
            change = true;
        }

        if (!change) {
            throw new Exception("No changes found");
        }
        Question updatedQuestion = question.get();
        questionRepository.save(updatedQuestion);
    }

    /**
     * Finds random questions based on the specified category and number of questions.
     *
     * @param category      the category of the questions
     * @param noOfQuestions the number of random questions to be retrieved
     * @return a list of random questions
     */
    @Override
    public List<Integer> findRandomQuestionsByCategory(String category, Integer noOfQuestions) {
        return questionRepository.findRandomQuestionsByCategory(category, noOfQuestions);
    }


    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer noOfQuestions) {

        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName, noOfQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer id : questionsIds) {
            questions.add(questionRepository.findById(id).get());
        }

        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper(question.getId(), question.getQuestion(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
            wrapper.id();
            wrapper.question();
            wrapper.option1();
            wrapper.option2();
            wrapper.option3();
            wrapper.option4();
            questionWrappers.add(wrapper);
        }


        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for (Response response : responses) {
            Question question = questionRepository.findById(response.id()).get();
            if (response.responseStatus().equals(question.getCorrectAns()))
                right++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }

}


















































