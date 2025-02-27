package dev.patika.definexjavaspringbootbootcamp2025.hw3.service;

import dev.patika.definexjavaspringbootbootcamp2025.hw3.dto.User;
import dev.patika.definexjavaspringbootbootcamp2025.hw3.exception.NoSuchUserException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final List<User> userList = new ArrayList<>();
    @Override
    public List<User> list() {
        return new ArrayList<>(userList);
    }

    @Override
    public User create(User user) {

        User newUser = User.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .name(user.getName())
                .licenseNumber(user.getLicenseNumber())
                .build();

        userList.add(newUser);
        return newUser;
    }

    @Override
    public User getUserProfile(UUID userId) throws NoSuchUserException {

        return userList.stream()
                .filter( user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(NoSuchUserException::new);
    }

    @Override
    public User update(User user) throws NoSuchUserException {

        User existingUser = getUserProfile(user.getId());

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setLicenseNumber(user.getLicenseNumber());

        return existingUser;
    }
}
