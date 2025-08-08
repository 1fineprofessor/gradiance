package com.uwec.gradiance.controller;

import com.uwec.gradiance.service.TestQueueService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ActiveQuestionController {
    private final TestQueueService queueService;

    public ActiveQuestionController(TestQueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/questions")
    public List<String> getQuestions() {
        return queueService.getAllQuestions();
    }

    @PostMapping("/active-question")
    public void setActiveQuestion(@RequestBody Map<String, Integer> payload) {
        Integer id = payload.get("id");
        if (id != null && id >= 0 && id < queueService.getAllQuestions().size()) {
            String q = queueService.getAllQuestions().get(id);
            queueService.setActiveQuestion(q);
        }
    }

    @GetMapping("/active-question")
    public Map<String, String> getActiveQuestion() {
        return Map.of("question", queueService.getActiveQuestion());
    }

    @GetMapping(path = "/active-question/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamActiveQuestion() {
        return queueService.registerQuestionEmitter();
    }
}