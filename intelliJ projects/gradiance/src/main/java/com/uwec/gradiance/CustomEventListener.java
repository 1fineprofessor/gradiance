package com.uwec.gradiance;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener {
    @EventListener
    public void onApplicationEvent(CustomEvent event){
        System.out.println("Received custom event from spring: " + event.getMessage());
    }
}
