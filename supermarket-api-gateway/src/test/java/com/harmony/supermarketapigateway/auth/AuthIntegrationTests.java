package com.harmony.supermarketapigateway.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harmony.supermarketapigateway.auth.dto.MemberRegistrationRequestDTO;
import com.harmony.supermarketapigateway.auth.entity.Member;
import com.harmony.supermarketapigateway.auth.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String MEMBER_ID = "devKingHyeok";
    private static final String VALID_PASSWORD = "hyeok12#$";
    private static final String SHORT_PASSWORD = "short";
    private static final String DUPLICATE_MEMBER_ERROR_CODE = "AUTH-ERROR-002";
    private static final String PASSWORD_VALIDATION_ERROR_MESSAGE = "password: 비밀번호는 최소 8자 이상이어야 합니다.";

    @BeforeEach
    public void cleanAllTestResource() { memberRepository.deleteAll(); }

    @Nested
    class 회원가입_통합_테스트 {

        @Test
        public void 회원가입_성공_테스트() throws Exception {
            // given
            MemberRegistrationRequestDTO expectSuccessRequestDTO = new MemberRegistrationRequestDTO(MEMBER_ID, VALID_PASSWORD);

            // when
            mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expectSuccessRequestDTO)))
                    // then
                    .andExpect(status().isOk())
                    .andExpect(content().string("회원가입 성공"));
        }

        @Test
        public void 회원ID가_이미_존재할_경우_에러_발생() throws Exception {
            // given
            memberRepository.save(new Member(MEMBER_ID, passwordEncoder.encode(VALID_PASSWORD)));
            MemberRegistrationRequestDTO expectDuplicatedRequestDTO = new MemberRegistrationRequestDTO(MEMBER_ID, VALID_PASSWORD);

            // when
            mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expectDuplicatedRequestDTO)))
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value(DUPLICATE_MEMBER_ERROR_CODE));
        }

        @Test
        public void 비밀번호_유효성_실패_테스트() throws Exception {
            // given
            MemberRegistrationRequestDTO expectPasswordTooShortRequestDTO = new MemberRegistrationRequestDTO(MEMBER_ID, SHORT_PASSWORD);

            // when
            mockMvc.perform(post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(expectPasswordTooShortRequestDTO)))
                    // then
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors[0]").value(PASSWORD_VALIDATION_ERROR_MESSAGE))
                    .andDo(print());
        }
    }
}