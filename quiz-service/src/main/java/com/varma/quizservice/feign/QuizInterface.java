package com.varma.quizservice.feign;


import com.varma.quizservice.model.QuestionWrapper;
import com.varma.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("api/v1/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
                                                             @RequestParam Integer noOfQuestions);

    @PostMapping("api/v1/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsIds);

    @PostMapping("api/v1/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}