package com.empik.githubrequest.service;

import com.empik.githubrequest.model.LoginRequestCount;
import com.empik.githubrequest.repository.LoginRequestCountRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginRequestCountService {

    private final LoginRequestCountRepository loginRequestCountRepository;
    private static final Long loginIncrement = 1L;

    public LoginRequestCountService(LoginRequestCountRepository loginRequestCountRepository) {
        this.loginRequestCountRepository = loginRequestCountRepository;
    }

    @Async
    public void saveLoginRequestCount(String login) {
        Optional<LoginRequestCount> loginRequestCountOptional = loginRequestCountRepository.findByLogin(login);
        if (loginRequestCountOptional.isPresent()) {
            LoginRequestCount loginRequestCount = loginRequestCountOptional.get();
            loginRequestCount.setREQUEST_COUNT(loginRequestCount.getREQUEST_COUNT() + loginIncrement);
            loginRequestCountRepository.save(loginRequestCount);
        } else {
            loginRequestCountRepository.save(new LoginRequestCount(login, loginIncrement));
        }
    }
}
