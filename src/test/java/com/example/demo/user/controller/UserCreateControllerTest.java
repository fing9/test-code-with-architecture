package com.example.demo.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.mock.TestContainer;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수_있고_회원_가입된_사용자는_PENDING_상태이다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .build();
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .build();

        // when
        ResponseEntity<UserResponse> result = UserCreateController.builder()
                .userCreateService(testContainer.userCreateService)
                .build()
                .createUser(userCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(result.getBody().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getBody().getLastLoginAt()).isNull();
    }

}
