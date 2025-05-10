package ru.webrise.subscription_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.webrise.subscription_service.DTO.SubscriptionDTO;
import ru.webrise.subscription_service.DTO.SubscriptionResponseDTO;
import ru.webrise.subscription_service.DTO.TopSubscriptionResponseDTO;
import ru.webrise.subscription_service.enums.ServiceName;
import ru.webrise.subscription_service.exception.IllegalNameException;
import ru.webrise.subscription_service.exception.SubscriptionNotFoundException;
import ru.webrise.subscription_service.exception.UserNotFoundException;
import ru.webrise.subscription_service.model.Subscription;
import ru.webrise.subscription_service.model.User;
import ru.webrise.subscription_service.repository.SubscriptionRepository;
import ru.webrise.subscription_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {
    private final Logger log = LoggerFactory.getLogger(SubscriptionService.class);
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public Subscription addSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        if (subscriptionDTO == null) {
            log.error("Переданы некорректные данные подписки: null");
            throw new IllegalArgumentException("Не удалось создать подписку: переданы некорректные данные");
        }
        if (!isValidServiceName(subscriptionDTO.getName())) {
            log.warn("Не удалось создать подписку: некорректное имя сервиса");
            throw new IllegalNameException("Имя сервиса подписки не должно быть пустым и должно содержать только буквы и пробелы");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с id: {} не найден при создании подписки", userId);
                    return new UserNotFoundException("Пользователь с id: " + userId + " не найден");
                });

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setName(ServiceName.fromString(subscriptionDTO.getName()));
        subscriptionRepository.save(subscription);

        log.info("Создана подписка id: {} для пользователя id: {}", subscription.getId(), userId);
        return subscription;
    }


    public List<Subscription> getUserSubscriptions(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.warn("Пользователь с id: {} не найден при получении подписок", userId);
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден");
        }
        return subscriptionRepository.findAllByUserId(userId);
    }

    public void removeSubscription(Long userId, Long subId) {
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> {
                    log.warn("Подписка с id: {} не найдена при удалении", subId);
                    return new SubscriptionNotFoundException("Подписка с id: " + subId + " не найдена");
                });

        if (!subscription.getUser().getId().equals(userId)) {
            log.warn("Подписка с id: {} не принадлежит пользователю с id: {}", subId, userId);
            throw new SubscriptionNotFoundException("Подписка с id: " + subId + " не принадлежит пользователю с id: " + userId);
        }

        subscriptionRepository.delete(subscription);
        log.info("Подписка с id: {} успешно удалена у пользователя с id: {}", subId, userId);
    }

    public List<SubscriptionResponseDTO> getUserSubscriptionsDto(Long userId) {
        List<Subscription> subscriptions = getUserSubscriptions(userId);
        List<SubscriptionResponseDTO> responseList = new ArrayList<>();
        for (Subscription sub : subscriptions) {
            responseList.add(new SubscriptionResponseDTO(sub.getId(), sub.getName().getDisplayName()));
        }
        return responseList;
    }

    public List<TopSubscriptionResponseDTO> getTop3Subscriptions() {
        List<Object[]> results = subscriptionRepository.findTop3Subscriptions();
        List<TopSubscriptionResponseDTO> topList = new ArrayList<>();
        for (Object[] row : results) {
            String subscriptionName = row[0].toString();
            long numberOfSubscribers = ((Number) row[1]).longValue();
            TopSubscriptionResponseDTO dto = new TopSubscriptionResponseDTO();
            dto.setSubscriptionName(subscriptionName);
            dto.setNumberOfSubscribers(numberOfSubscribers);
            topList.add(dto);
        }
        return topList;
    }

    private boolean isValidServiceName(String serviceName) {
        return serviceName != null && !serviceName.isBlank();
    }
}