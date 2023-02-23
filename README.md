## Cascade - 영속성 전이
- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티로 함께 영속 상태로 만드는 것

### 영속성 전이 - 저장
- cascadeType.PERSIST 를 통해 엔티티를 영속화할 때 연관된 엔티티도 영속화한다.
```java
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Member> members = new ArrayList<>();

    @Builder
    public Team(String teamName) {
        this.teamName = teamName;
    }
}

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @Builder
    public Member(Long id, String username, Team team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;
}

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

        // 연관된 엔티티를 참조하고 있어야 한다.
        team.getMembers().add(member);

        teamRepository.save(team);
    }
}
```
```sql
-- 위의 테스트 코드 실행 결과
-- team 엔티티만을 save 했지만 member 엔티티 또한 insert 됐음을 알 수 있다.
Hibernate: 
    insert 
    into
        team
        (team_name, id) 
    values
        (?, ?)
Hibernate: 
    insert 
    into
        member
        (team_id, username, id) 
    values
        (?, ?, ?)
```

### 영속성 전이 - 삭제
- cascadeType.REMOVE 를 통해 엔티티를 제거할 때 연관된 엔티티도 함께 제거할 수 있다.
- cascadeType.REMOVE 를 설정하지 않고 부모 엔티티를 제거 시 데이터베이스의 자식 테이블에 걸려 있는
외래 키 제약조건으로 인해 외래키 무결성 예외가 발생할 수 있다.(자식이 삭제된 부모의 키를 참조)
- 연관된 자식 엔티티가 많을 경우 자식 엔티티의 개수만큼 delete 가 각각 처리되어 성능상 문제가 될 수 있다.
  - 벌크 연산을 통해 부모 엔티티에 연관된 자식 엔티티들을 한 번에 delete 처리하는 것이 좋다.
```java
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<Member> members = new ArrayList<>();

    @Builder
    public Team(String teamName) {
        this.teamName = teamName;
    }
}

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @Builder
    public Member(Long id, String username, Team team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;
}

@SpringBootTest
class TeamTest {

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    MemberRepository memberRepository;

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
```
```sql
-- 위의 테스트 코드 실행 결과
-- team 엔티티를 삭제할 때 연관된 member 도 삭제한다.
-- member 엔티티 별로 각각 delete 처리하기 때문에 연관된 자식 엔티티가 많다면 성능상 문제가 될 수 있다.
Hibernate: 
    delete 
    from
        member 
    where
        id=?
Hibernate: 
    delete 
    from
        member 
    where
        id=?
Hibernate: 
    delete 
    from
        team 
    where
        id=?
```

### 고아 객체 제거
- JPA는 부모 엔티티와 연관관계가 끊어진 자식 인테티를 자동으로 삭제하는 기능을 제공
  - 고아 객체
    - 부모 엔티티와 연관관계가 끊어진 자식 엔티티
```java
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;

    // team 엔티티 삭제시 연관된 자식 member 엔티티들도 자동으로 삭제
    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Builder
    public Team(String teamName) {
        this.teamName = teamName;
    }
}

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @Builder
    public Member(Long id, String username, Team team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;
}
```