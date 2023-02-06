package ruby.jpalearn.entity;

import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @OneToOne
    private Locker locker;

    private void setTeam(Team team) {
        this.team = team;
    }
}
