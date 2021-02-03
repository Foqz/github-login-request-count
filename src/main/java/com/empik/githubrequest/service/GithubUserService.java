package com.empik.githubrequest.service;

import com.empik.githubrequest.dto.GithubUserByLoginResponse;
import com.empik.githubrequest.dto.MappedGithubUserResponse;
import com.empik.githubrequest.exceptions.GithubUserResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GithubUserService {

    @Value("${baseGithubURL}")
    private String baseGithubURL;
    private final LoginRequestCountService loginRequestCountService;

    public GithubUserService(LoginRequestCountService loginRequestCountService) {
        this.loginRequestCountService = loginRequestCountService;
    }

    public MappedGithubUserResponse getGithubInfoByLogin(String login) {
        loginRequestCountService.saveLoginRequestCount(login);
        return mapGithubUserInfo(getGithubUserResponseByLogin(login));
    }

    private GithubUserByLoginResponse getGithubUserResponseByLogin(String login) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(baseGithubURL + login)
                    .build();

            Response response = client.newCall(request).execute();

            return mapper.readValue(response.body().byteStream(), GithubUserByLoginResponse.class);
        } catch (IOException e) {
            throw new GithubUserResponseException("Exception during connection to github API");
        }
    }

    private MappedGithubUserResponse mapGithubUserInfo(GithubUserByLoginResponse githubUserByLoginResponse) {
        return MappedGithubUserResponse.builder()
                .id(githubUserByLoginResponse.getId())
                .login(githubUserByLoginResponse.getLogin())
                .name(githubUserByLoginResponse.getName())
                .type(githubUserByLoginResponse.getType())
                .avatarUrl(githubUserByLoginResponse.getAvatar_url())
                .createdAt(githubUserByLoginResponse.getCreated_at())
                .calculations(calculate(githubUserByLoginResponse.getFollowers(), githubUserByLoginResponse.getPublic_repos()))
                .build();
    }

    private double calculate(Long followers, Long publicRepos) {
        double followersFloat = followers;
        double publicReposFloat = publicRepos;
        return 6 / followersFloat * (2 + publicReposFloat);
    }
}
