package com.harmony.supermarketapigateway.auth.security;

import com.harmony.supermarketapigateway.auth.entity.Member;
import com.harmony.supermarketapigateway.auth.repository.MemberRepository;
import com.harmony.supermarketapigateway.exception.ErrorCode.AuthErrorCode;
import com.harmony.supermarketapigateway.exception.SupermarketAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            return findMember.toUserDetails();
        } catch (SupermarketAuthException e) {
            log.error("Authentication error for memberId: {}. Error code: {}", memberId, e.getErrorCode());
            throw e;
        }
    }
}