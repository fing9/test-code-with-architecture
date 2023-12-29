package com.example.demo.post.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestContainer;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회할_수_있다() {
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
        Post post = Post.builder()
                .content("helloworld")
                .writer(user)
                .build();
        testContainer.postRepository.save(post);

        // when
        ResponseEntity<PostResponse> result = PostController.builder()
                .postService(testContainer.postService)
                .userController(testContainer.userController)
                .build()
                .getPostById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getWriter().getLastLoginAt()).isEqualTo(0L);
    }

    @Test
    void 사용자는_존재하지_않는_게시물을_조회할_경우_에러가_난다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            PostController.builder()
                    .postService(testContainer.postService)
                    .userController(testContainer.userController)
                    .build()
                    .getPostById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() {
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
        Post post = Post.builder()
                .content("helloworld")
                .writer(user)
                .build();
        testContainer.postRepository.save(post);
        PostUpdate postUpdate = PostUpdate.builder()
                .content("helloworld :)")
                .build();

        // when
        ResponseEntity<PostResponse> result = PostController.builder()
                .postService(testContainer.postService)
                .userController(testContainer.userController)
                .build()
                .updatePost(1, postUpdate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld :)");
        assertThat(result.getBody().getModifiedAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getWriter().getLastLoginAt()).isEqualTo(0L);
    }

}
