package ruby.jpalearn.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruby.jpalearn.repository.MemberRepository;
import ruby.jpalearn.repository.TeamRepository;

@SpringBootTest
class TeamTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void testCascadePersist() {
        Team team = Team.builder()
                .teamName("team")
                .build();

        Member member = Member.builder()
                .username("ruby")
                .team(team)
                .build();

        team.getMembers().add(member);

        teamRepository.save(team);
    }

    @Test
    void testCascadeRemove() {
        Team team = Team.builder()
                .teamName("team")
                .build();
        teamRepository.save(team);

        Member member1 = Member.builder()
                .username("ruby1")
                .team(team)
                .build();
        memberRepository.save(member1);
        Member member2 = Member.builder()
                .username("ruby2")
                .team(team)
                .build();
        memberRepository.save(member2);

        team.getMembers().add(member1);
        team.getMembers().add(member2);
        teamRepository.delete(team);
    }
}