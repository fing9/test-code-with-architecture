package com.example.demo.post.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.user.domain.UserStatus;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/post-service-test-data.sql")
public class PostJpaRepositoryTest {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Test
    public void findById_로_게시물을_조회할_수_있다() {
        // given
        // when
        Optional<PostEntity> result = postJpaRepository.findById(1L);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(1);
        assertThat(result.get().getContent()).isEqualTo("helloworld");
        assertThat(result.get().getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(result.get().getModifiedAt()).isEqualTo(0);
        assertThat(result.get().getWriter().getId()).isEqualTo(1);
        assertThat(result.get().getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.get().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.get().getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(result.get().getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(result.get().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.get().getWriter().getLastLoginAt()).isEqualTo(0);
    }
}
