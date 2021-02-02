package com.empik.githubrequest.repository;

import com.empik.githubrequest.model.LoginRequestCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRequestCountRepository extends JpaRepository<LoginRequestCount, String> {
    Optional<LoginRequestCount> findByLogin(String login);
}
