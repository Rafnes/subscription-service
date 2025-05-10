package ru.webrise.subscription_service.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.webrise.subscription_service.DTO.CreateOrUpdateUserDTO;
import ru.webrise.subscription_service.exception.IllegalNameException;
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
            log.error("Передан null пользователь");
            throw new IllegalArgumentException("Не удалось создать пользователя: переданы некорректные данные");
        }
        if (!isValidName(userDTO.getName())) {
            log.warn("Не удалось обновить пользователя: некорректное имя");
            throw new IllegalNameException("Имя пользователя не должно быть пустым и должно содержать только буквы");
        }
        User user = new User();
        user.setName(userDTO.getName());
        userRepository.save(user);
        return user;
    }

    public User getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + id + " не найден"));
        return user;
    }

    public User updateUser(Long id, CreateOrUpdateUserDTO userDTO) {
        if (!isValidName(userDTO.getName())) {
            log.warn("Не удалось обновить пользователя: некорректное имя");
            throw new IllegalNameException("Имя пользователя не должно быть пустым и должно содержать только буквы");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + id + " не найден"));
        user.setName(userDTO.getName());
        userRepository.save(user);
        log.info("Обновлен пользователь: id: {}, имя: {}", id, user.getName());
        return user;
    }

    @Transactional
    public void removeUser(Long id) {
        if (!userRepository.existsById(id)) {
            log.warn("Пользователь с id: {} не найден", id);
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        }
        userRepository.deleteById(id);
        log.info("Пользователь с id: {} успешно удалён", id);
    }

    private boolean isValidName(String name) {
        return !name.isBlank() && StringUtils.isAlphaSpace(name);
    }
}
