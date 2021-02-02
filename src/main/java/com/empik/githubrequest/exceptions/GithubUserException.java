package com.empik.githubrequest.exceptions;

public class GithubUserException extends RuntimeException{
    public GithubUserException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
