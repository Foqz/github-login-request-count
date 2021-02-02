package com.empik.github.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RequestCount {

    @Id
    private String login;

    private Long REQUEST_COUNT = 0L;
}
