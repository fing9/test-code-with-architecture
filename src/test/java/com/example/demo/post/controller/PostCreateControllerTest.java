package com.example.demo.post.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestContainer;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_생성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();
        User user = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(0L)
                .build();
        testContainer.userRepository.save(user);
        PostCreate postCreate = PostCreate.builder()
                .content("helloworld")
                .writerId(1L)
                .build();

        // when
        ResponseEntity<PostResponse> result = PostCreateController.builder()
                .postService(testContainer.postService)
                .build()
                .createPost(postCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getWriter().getLastLoginAt()).isEqualTo(0L);
    }

}
