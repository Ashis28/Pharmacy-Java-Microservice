package com.pharmacy.notification.controller;

import com.pharmacy.notification.dto.NotificationRequest;
import com.pharmacy.notification.listener.NotificationListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationListener notificationListener;

    /**
     * Fallback HTTP endpoint - for direct calls or testing.
     * Primary path is via RabbitMQ queue.
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody NotificationRequest req) {
        notificationListener.handleNotification(req);
        return ResponseEntity.ok("Notification processed for: " + req.getRecipientEmail());
    }
}
