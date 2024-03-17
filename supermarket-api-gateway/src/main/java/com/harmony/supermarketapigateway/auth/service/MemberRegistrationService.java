package com.harmony.supermarketapigateway.auth.service;


import com.harmony.supermarketapigateway.auth.dto.MemberRegistrationRequestDTO;
import com.harmony.supermarketapigateway.auth.entity.Member;
import com.harmony.supermarketapigateway.auth.repository.MemberRepository;
import com.harmony.supermarketapigateway.exception.ErrorCode.AuthErrorCode;
import com.harmony.supermarketapigateway.exception.SupermarketAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberRegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member registerMember(final MemberRegistrationRequestDTO memberRegistrationRequestDTO) {
        Optional<Member> existingMember = memberRepository.findByMemberId(memberRegistrationRequestDTO.getMemberId());
        if (existingMember.isPresent()) {
            throw new SupermarketAuthException(
                    AuthErrorCode.DUPLICATE_MEMBER_ID.getCode(),
                    AuthErrorCode.DUPLICATE_MEMBER_ID.getMessage()
            );
        }

        Member newMember = Member.createWithEncodedPassword(memberRegistrationRequestDTO.getMemberId(), passwordEncoder.encode(memberRegistrationRequestDTO.getPassword()));
        return memberRepository.save(newMember);
    }
}
