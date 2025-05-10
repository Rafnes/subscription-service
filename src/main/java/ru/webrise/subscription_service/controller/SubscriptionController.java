package ru.webrise.subscription_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webrise.subscription_service.DTO.SubscriptionDTO;
import ru.webrise.subscription_service.DTO.SubscriptionResponseDTO;
import ru.webrise.subscription_service.DTO.TopSubscriptionResponseDTO;
import ru.webrise.subscription_service.model.Subscription;
import ru.webrise.subscription_service.service.SubscriptionService;

import java.util.List;

@RestController
@Tag(name = "Подписки", description = "Управление подписками")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Добавить подписку пользователю", description = "Создаёт новую подписку для пользователя"
    )
    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<Subscription> createSubscription(@PathVariable Long userId,
                                                           @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(subscriptionService.addSubscription(userId, subscriptionDTO));
    }

    @Operation(summary = "Получить подписки пользователя")
    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserSubscriptions(@PathVariable Long userId) {
        List<SubscriptionResponseDTO> subscriptionsDto = subscriptionService.getUserSubscriptionsDto(userId);
        return ResponseEntity.ok(subscriptionsDto);
    }

    @Operation(summary = "Удалить подписку пользователя")
    @DeleteMapping("/users/{userId}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId,
                                                   @PathVariable Long subId) {
        subscriptionService.removeSubscription(userId, subId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Топ-3 популярных подписок")
    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<TopSubscriptionResponseDTO>> getTopSubscriptions() {
        List<TopSubscriptionResponseDTO> topSubscriptions = subscriptionService.getTop3Subscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}
