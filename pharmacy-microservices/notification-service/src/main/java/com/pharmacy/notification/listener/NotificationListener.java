package com.pharmacy.notification.listener;

import com.pharmacy.notification.config.RabbitMQConfig;
import com.pharmacy.notification.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleNotification(NotificationRequest request) {
        log.info("[NOTIFICATION] type={} to={} subject={}",
                request.getType(), request.getRecipientEmail(), request.getSubject());
        log.info("[NOTIFICATION] message={}", request.getMessage());
        // TODO: integrate JavaMailSender / Twilio / Firebase here
    }
}
