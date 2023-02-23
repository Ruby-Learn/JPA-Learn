package ruby.jpalearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.jpalearn.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
