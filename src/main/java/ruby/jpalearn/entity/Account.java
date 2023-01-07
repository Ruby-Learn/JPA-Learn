package ruby.jpalearn.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACCOUNT")
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
//    @Temporal(TemporalType.TIMESTAMP) - 자바의 LocalDate, LocalDateTime 으로 데이터베이스의 data, Timestamp 타입과 매핑 가능
    private LocalDate createDate;
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateDate;
}
