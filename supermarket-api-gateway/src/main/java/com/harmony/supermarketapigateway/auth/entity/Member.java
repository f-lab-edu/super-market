package com.supermarket.gateway.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Entity
@Getter
public class Member {
    @Id
    private String memberId;
    private String password;

    protected Member() {};

    public static Member createWithEncodedPassword(final String memberId, final String encodedPassword) {
        return new Member(memberId, encodedPassword);
    }
}
