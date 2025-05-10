package ru.webrise.subscription_service.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopSubscriptionResponseDTO {
    private String subscriptionName;
    private long numberOfSubscribers;
}
