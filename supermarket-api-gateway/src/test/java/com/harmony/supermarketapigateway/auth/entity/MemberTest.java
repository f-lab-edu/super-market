package com.harmony.supermarketapigateway.auth.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Nested
    @DisplayName("멤버에 대한 보안성 체크")
    class MemberSecurityCheck {
        private String memberIdForSecurityTest = "hyeok";
        private String passwordForSecurityTest = "암호화된 비밀번호";

        /**
         * 취약점 기록: member에 대한 getter를 없애 고객 정보를 직접 조회할 순 없게됬지만 인증 과정을 위해 추출
         **/
        @Test
        @DisplayName("취약점 존재: member를 통해 얻을 수 있는 userDetails를 통해 여전히 유저 정보를 조회 가능")
        void testUserDetailsExposePassword(){
            // given
            Member targetMember = Member.createWithEncodedPassword(memberIdForSecurityTest, passwordForSecurityTest);

            // when
            String exportedPassword = targetMember.toUserDetails().getPassword();

            // then
            assertEquals(passwordForSecurityTest, exportedPassword);
        }
    }
}