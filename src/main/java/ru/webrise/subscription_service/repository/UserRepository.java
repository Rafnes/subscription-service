package ru.webrise.subscription_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.webrise.subscription_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
