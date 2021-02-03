package com.empik.githubrequest.exceptions;

public class GithubUserResponseException extends RuntimeException{
    public GithubUserResponseException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
