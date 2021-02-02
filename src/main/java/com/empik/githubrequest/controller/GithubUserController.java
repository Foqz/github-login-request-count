package com.empik.githubrequest.controller;

import com.empik.githubrequest.dto.MappedGithubUserResponse;
import com.empik.githubrequest.service.GithubUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class GithubUserController {

    private final GithubUserService githubUserService;

    @GetMapping("/{login}")
    public ResponseEntity<MappedGithubUserResponse> getGithubInfoByLogin(@PathVariable String login) {
        return new ResponseEntity<>(githubUserService.getGithubInfoByLogin(login), HttpStatus.OK);
    }
}
