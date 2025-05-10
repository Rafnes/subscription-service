package ru.webrise.subscription_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.webrise.subscription_service.model.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByUserId(Long userId);

    @Query(value = "SELECT name, COUNT(*) AS total FROM subscriptions GROUP BY name ORDER BY total DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3Subscriptions();
}
