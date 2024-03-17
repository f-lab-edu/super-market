package com.harmony.supermarketapigateway.auth.controller;

import com.harmony.supermarketapigateway.auth.dto.MemberRegistrationRequestDTO;
import com.harmony.supermarketapigateway.auth.service.MemberRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class MemberRegistrationController {
    private final MemberRegistrationService memberRegistrationService;

    @PostMapping()
    public ResponseEntity<String> registerUser(@Valid @RequestBody final MemberRegistrationRequestDTO memberRegistrationRequestDTO) {
        memberRegistrationService.registerMember(memberRegistrationRequestDTO);

        return ResponseEntity.ok("회원가입 성공");
    }
}
