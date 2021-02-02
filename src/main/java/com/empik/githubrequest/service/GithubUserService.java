package com.empik.githubrequest.service;

import com.empik.githubrequest.dto.GithubUserByLoginResponse;
import com.empik.githubrequest.dto.MappedGithubUserResponse;
import com.empik.githubrequest.exceptions.GithubUserException;
import com.empik.githubrequest.model.LoginRequestCount;
import com.empik.githubrequest.repository.LoginRequestCountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class GithubUserService {

    @Value("${baseGithubURL}")
    private String baseGithubURL;
    private static final Long loginIncrement = 1L;

    private final LoginRequestCountRepository loginRequestCountRepository;

    public GithubUserService(LoginRequestCountRepository loginRequestCountRepository) {
        this.loginRequestCountRepository = loginRequestCountRepository;
    }

    public MappedGithubUserResponse getGithubInfoByLogin(String login) {
        GithubUserByLoginResponse githubUserByLoginResponse = getGithubUserResponseByLogin(login);
        saveLoginRequestCount(login);
        return mapGithubUserInfo(githubUserByLoginResponse);
    }

    @Transactional
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
            throw new GithubUserException("Exception during connection to github API");
        }
    }
}
