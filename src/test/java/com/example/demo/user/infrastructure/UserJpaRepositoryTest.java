package com.example.demo.user.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
@Sql("/sql/user-repository-test-data.sql")
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(1);
        assertThat(result.get().getEmail()).isEqualTo("djajsl54@naver.com");
        assertThat(result.get().getNickname()).isEqualTo("seongmik");
        assertThat(result.get().getAddress()).isEqualTo("Seoul");
        assertThat(result.get().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(result.get().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.get().getLastLoginAt()).isEqualTo(0);
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("djajsl54@naver.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(1);
        assertThat(result.get().getEmail()).isEqualTo("djajsl54@naver.com");
        assertThat(result.get().getNickname()).isEqualTo("seongmik");
        assertThat(result.get().getAddress()).isEqualTo("Seoul");
        assertThat(result.get().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(result.get().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.get().getLastLoginAt()).isEqualTo(0);
    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given

        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("djajsl54@naver.com", UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }


}
