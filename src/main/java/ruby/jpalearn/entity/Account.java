package ruby.jpalearn.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MEMBER")
@SequenceGenerator(
        name = "ACCOUNT_SEQ_GENERATOR",
        sequenceName = "ACCOUNT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Account {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ACCOUNT_SEQ_GENERATOR"
    )
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "NAME")
    private String username;
    @Column(name = "AGE")
    private Integer age;
    @Lob
    private String description;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate updateDate;
}
