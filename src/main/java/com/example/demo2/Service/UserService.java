package com.example.demo2.Service;

import com.example.demo2.model.UserEntity;
import com.example.demo2.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)) {
            log.warn("Email alreadt exists {}" ,email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(userEntity);
    }
    public UserEntity getByCredentials(final String email, final String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }
}
