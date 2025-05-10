package ru.webrise.subscription_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.webrise.subscription_service.model.Subscription;

@Getter
@Setter
@AllArgsConstructor
public class SubscriptionResponseDTO {
    private Long id;
    private String name;
}
