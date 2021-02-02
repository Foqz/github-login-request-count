package com.empik.githubrequest.repository;

import com.empik.githubrequest.model.LoginCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginCountRepository extends JpaRepository<LoginCount, String> {
    Optional<LoginCount> findByLogin(String login);
}
