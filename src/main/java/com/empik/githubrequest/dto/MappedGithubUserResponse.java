package com.empik.githubrequest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MappedGithubUserResponse {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Date createdAt;
    private double calculations;
}
