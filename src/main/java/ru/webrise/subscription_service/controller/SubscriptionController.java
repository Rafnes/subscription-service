package ru.webrise.subscription_service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import ru.webrise.subscription_service.service.SubscriptionService;

@RestController
@Tag(name = "Подписки", description = "Операции для работы с подписками")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
}
