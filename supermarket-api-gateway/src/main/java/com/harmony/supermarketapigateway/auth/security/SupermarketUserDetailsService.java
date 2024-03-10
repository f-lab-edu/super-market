package com.supermarket.gateway.auth.security;

import com.supermarket.gateway.auth.entity.Member;
import com.supermarket.gateway.auth.exception.ErrorCode.AuthErrorCode;
import com.supermarket.gateway.auth.exception.SupermarketAuthException;
import com.supermarket.gateway.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class SupermarketUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String memberId) throws UsernameNotFoundException {
        try {
            Member findMember = memberRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new SupermarketAuthException(
                            AuthErrorCode.USER_NOT_FOUND.getCode(),
                            AuthErrorCode.USER_NOT_FOUND.getMessage()
                    ));
            return new User(findMember.getMemberId(), findMember.getPassword(), new ArrayList<>());
        } catch (SupermarketAuthException e) {
            log.error("Authentication error for memberId: {}. Error code: {}", memberId, e.getErrorCode());
            throw e;
        }
    }
}