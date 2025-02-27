package dev.patika.definexjavaspringbootbootcamp2025.hw3.service;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.User;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchUserException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> list();
    User create(User user);
    User getUserProfile(UUID userId) throws NoSuchUserException;
    User update(User user) throws NoSuchUserException;
}
