package ruby.jpalearn.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruby.jpalearn.repository.MemberRepository;
import ruby.jpalearn.repository.TeamRepository;

import java.util.Optional;

@SpringBootTest
class MemberTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void testEager() {
        Team team = Team.builder()
                .teamName("team")
                .build();
        teamRepository.save(team);

        Member member = Member.builder()
                .username("ruby")
                .team(team)
                .build();
        memberRepository.save(member);

        memberRepository.findById(member.getId()).orElse(null);
    }
}