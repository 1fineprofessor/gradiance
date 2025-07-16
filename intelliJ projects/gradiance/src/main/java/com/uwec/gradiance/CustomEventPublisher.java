package com.uwec.gradiance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

public class CustomEventPublisher {
    @Component
    public class CustomSpringEventPublisher {
        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;

        public void publishCustomEvent(final String message) {
            System.out.println("Publishing custom event. ");
            CustomEvent customSpringEvent = new CustomEvent(this, message);
            applicationEventPublisher.publishEvent(customSpringEvent);
        }
    }
}
