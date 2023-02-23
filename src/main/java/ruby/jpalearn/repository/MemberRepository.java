package ruby.jpalearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.jpalearn.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
