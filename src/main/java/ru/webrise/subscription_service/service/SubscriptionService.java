package ru.webrise.subscription_service.service;

import org.springframework.stereotype.Service;
import ru.webrise.subscription_service.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


}
