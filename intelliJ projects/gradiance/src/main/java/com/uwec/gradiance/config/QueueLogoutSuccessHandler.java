package com.uwec.gradiance.config;

import com.uwec.gradiance.service.TestQueueService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueueLogoutSuccessHandler implements LogoutSuccessHandler {
    private final TestQueueService queueService;

    public QueueLogoutSuccessHandler(TestQueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
            queueService.clearForUser(authentication.getName());
        }
        response.sendRedirect(request.getContextPath() + "/login?logout");
    }
}