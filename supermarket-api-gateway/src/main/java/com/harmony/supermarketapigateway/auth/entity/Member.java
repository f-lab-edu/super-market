package com.harmony.supermarketapigateway.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@AllArgsConstructor
@Entity
public class Member {
    @Id
    private String memberId;
    private String password;

    protected Member() {};

    public static Member createWithEncodedPassword(final String memberId, final String encodedPassword) {
        return new Member(memberId, encodedPassword);
    }

    public User toUserDetails(){
        return new User(this.memberId, this.password, new ArrayList<>());
    }
}
