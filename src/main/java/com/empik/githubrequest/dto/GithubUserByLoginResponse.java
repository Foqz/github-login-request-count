package com.empik.githubrequest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUserByLoginResponse {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatar_url;
    private Date created_at;
    private Long public_repos;
    private Long followers;
}
