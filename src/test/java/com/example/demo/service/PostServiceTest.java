package com.example.demo.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.model.dto.PostCreateDto;
import com.example.demo.model.dto.PostUpdateDto;
import com.example.demo.repository.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById_는_존재하는_게시물을_내려준다() {
        // given
        // when
        PostEntity result = postService.getById(1);

        // then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getWriter().getId()).isEqualTo(1);
    }

    @Test
    void create_는_PostCreateDto_로_게시물을_생성할_수_있다() {
        // given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("foobar")
                .build();

        // when
        PostEntity result = postService.create(postCreateDto);

        // then
        assertThat(result.getContent()).isEqualTo("foobar");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getWriter().getId()).isEqualTo(1);
    }

    @Test
    void update_는_PostUpdateDto_로_게시물을_수정할_수_있다() {
        // given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("hello world!")
                .build();

        // when
        PostEntity result = postService.update(1, postUpdateDto);

        // then
        assertThat(result.getContent()).isEqualTo("hello world!");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getWriter().getId()).isEqualTo(1);
    }
}
