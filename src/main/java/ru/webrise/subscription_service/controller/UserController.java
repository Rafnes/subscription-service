package ru.webrise.subscription_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webrise.subscription_service.DTO.CreateOrUpdateUserDTO;
import ru.webrise.subscription_service.model.User;
import ru.webrise.subscription_service.service.UserService;

@Tag(name = "Пользователи", description = " пользователями")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateOrUpdateUserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @Operation(summary = "Получить инфо о пользователе", description = "Возвращает инфо о пользователе с указанным id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @Operation(summary = "Обновить пользователя", description = "Обновляет пользователя с указанным id")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody CreateOrUpdateUserDTO dto) {
        User updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя с указанным id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}
