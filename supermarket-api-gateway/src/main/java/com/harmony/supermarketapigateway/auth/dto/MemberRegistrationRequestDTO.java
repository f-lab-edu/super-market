package com.supermarket.gateway.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberRegistrationRequestDTO {
    @NotBlank(message = "회원 ID는 필수입니다.")
    private final String memberId;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private final String password;

    public MemberRegistrationRequestDTO(@JsonProperty("memberId") String memberId, @JsonProperty("password") String password) {
        this.memberId = memberId;
        this.password = password;
    }
}
