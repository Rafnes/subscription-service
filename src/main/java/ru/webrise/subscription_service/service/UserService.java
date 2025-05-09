package ru.webrise.subscription_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.webrise.subscription_service.DTO.CreateOrUpdateUserDTO;
import ru.webrise.subscription_service.exception.UserNotFoundException;
import ru.webrise.subscription_service.model.User;
import ru.webrise.subscription_service.repository.UserRepository;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateOrUpdateUserDTO userDTO) {
        if (userDTO == null) {
            log.error("Передан null ---");
            throw new IllegalArgumentException("User null");
        }
        User user = new User();
        user.setName(userDTO.getName());
        userRepository.save(user);
        return user;
    }

    public User getUserInfo(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("Пользователь с id: {} не найден", id);
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        });
    }

    public User updateUser(Long id, CreateOrUpdateUserDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    log.info("Редактируем пользователя с id: {}", id);
                    user.setName(dto.getName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> {
                    log.warn("Пользователь с id: {} не найден", id);
                    return new UserNotFoundException("Пользователь не найден");
                });
    }

    public void removeUser(Long id) {
        log.info("Удаляем пользователя с id: {}", id);
        if (!userRepository.existsById(id)) {
            log.warn("Пользователь с id: {} не найден", id);
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        }
        userRepository.deleteById(id);
        log.info("Пользователь с id: {} успешно удалён", id);
    }
}
