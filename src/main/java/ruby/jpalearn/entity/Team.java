package ruby.jpalearn.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;

    @OneToMany
    private List<Member> members = new ArrayList<>();
}
