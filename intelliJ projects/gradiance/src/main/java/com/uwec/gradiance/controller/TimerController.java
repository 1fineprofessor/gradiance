package com.uwec.gradiance.controller;

import com.uwec.gradiance.dto.TimerDtos.QuestionEndRequest;
import com.uwec.gradiance.dto.TimerDtos.SessionEndRequest;
import com.uwec.gradiance.dto.TimerDtos.TimerAck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timers")
public class TimerController {

    @PostMapping("/question/end")
    public ResponseEntity<TimerAck> questionEnd(@RequestBody QuestionEndRequest req) {
        if (req.timerId() == null || req.timerId().isBlank()) {
            return ResponseEntity.badRequest().body(new TimerAck("bad_request", null));
        }
        final boolean invalid = req.validation() != null
                && (req.validation().lt10s() || req.validation().gt1h());

        return ResponseEntity.ok(new TimerAck(invalid ? "accepted_flagged" : "accepted", req.timerId()));
    }

    @PostMapping("/session/end")
    public ResponseEntity<TimerAck> sessionEnd(@RequestBody SessionEndRequest req) {
        if (req.timerId() == null || req.timerId().isBlank()) {
            return ResponseEntity.badRequest().body(new TimerAck("bad_request", null));
        }
        final boolean invalid = req.validation() != null
                && (req.validation().lt10s() || req.validation().gt1h());

        return ResponseEntity.ok(new TimerAck(invalid ? "accepted_flagged" : "accepted", req.timerId()));
    }
}