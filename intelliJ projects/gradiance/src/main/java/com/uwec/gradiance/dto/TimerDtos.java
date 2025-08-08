package com.uwec.gradiance.dto;

/**
 * Single-file DTO holder for timer endpoints.
 * Exposes nested public static records so callers in other packages
 * can reference them as TimerDtos.QuestionEndRequest, etc.
 */
public final class TimerDtos {

    private TimerDtos() {} // no instances

    public static record QuestionEndRequest(
            String timerId,
            String studentId,
            String questionId,
            long elapsedMs,
            long startedAt,
            long endedAt,
            ValidationFlags validation,
            String pageContext
    ) {
        public static record ValidationFlags(boolean lt10s, boolean gt1h) {}
    }

    public static record SessionEndRequest(
            String timerId,
            String studentId,
            long elapsedMs,
            long startedAt,
            long endedAt,
            ValidationFlags validation,
            String pageContext
    ) {
        public static record ValidationFlags(boolean lt10s, boolean gt1h) {}
    }

    public static record TimerAck(String status, String timerId) {}
}