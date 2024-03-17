package com.harmony.supermarketapigateway.auth.entity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Nested
    class 멤버_객체에_대한_보안성_체크 {
        private String memberIdForSecurityTest = "hyeok";
        private String passwordForSecurityTest = "암호화된 비밀번호";

        /**
         * Member의 getter()를 통해 비밀번호에 접근하는 것은 막았으나, 이렇게 해도 결국 멤버를 통해 받은 UserDetails를 통해한 비밀번호 유출은 막을 수 없다.
         */
        @Test
        void getter와_toString에_의한_비밀번호_노출_된다(){
            // given
            Member targetMember = Member.createWithEncodedPassword(memberIdForSecurityTest, passwordForSecurityTest);

            // when
            String exportPassword = targetMember.toUserDetails().getPassword();

            // then
            assertEquals(passwordForSecurityTest, exportPassword);
        }
    }
}