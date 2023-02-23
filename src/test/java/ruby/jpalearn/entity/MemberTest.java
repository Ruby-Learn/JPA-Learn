package ruby.jpalearn.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruby.jpalearn.repository.MemberRepository;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testEager() {
        memberRepository.findAll();
    }
}