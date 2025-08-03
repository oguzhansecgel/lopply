package com.lopply.auth_service.service;

import com.lopply.auth_service.dto.GetSubscriberIdByUid;
import com.lopply.auth_service.entity.User;
import com.lopply.auth_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<GetSubscriberIdByUid> getSubscriberIdByUid(UUID uid) {
        Optional<User> user = userRepository.findByUuid(uid);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        User existingUser = user.get();
        GetSubscriberIdByUid response = new GetSubscriberIdByUid();
        response.setId(existingUser.getId());
        return Optional.of(response);
    }
}
