## 즉시로딩 - Eager Loading
- 엔티티를 조회할 때 연관된 엔티티로 함께 조회
  - 연관된 엔티티를 별도의 쿼리 작성 없이 조회할 수 있다는 장점이 있지만 연관된 엔티티를 사용하지 않을 경우에도
  불필요하게 조회할 수 있으므로 즉시로딩은 권장되지 않는다.
- @ManyToOne 의 fetch 속성을 FetchType.EAGER 로 지정
```java
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
    @JoinColumn(name = "team_id")
    private Team team;
}


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
```
```sql
-- 즉시로딩을 통해 특정 엔티티를 조회한 결과. Join 을 통해 연관된 엔티티도 함께 조회한다.
select
    member0_.id as id1_0_0_,
    member0_.team_id as team_id3_0_0_,
    member0_.username as username2_0_0_,
    team1_.id as id1_1_1_,
    team1_.team_name as team_nam2_1_1_ 
from
    member member0_ 
left outer join
    team team1_ 
        on member0_.team_id=team1_.id 
where
    member0_.id=?
```
*즉시로딩을 통해 조회시 기본적으로 연관된 엔티티를 외부조인, LEFT OUTER JOIN 을 사용하여 조회한다.
이는 JPA 에서 연관된 엔티티에 NULL 을 허용할 수 있기 때문이다.
외부조인이 아닌 내부조인을 사용하여 연관 엔티티를 조회하려면 @JoinColumn(nullable = false) 옵션을 설정해줘야 한다.*

## 지연로딩 - Lazy Loading
- 연관된 엔티티를 실제 사용하는 시점에 조회
  - 실제 사용하기 전까지는 연관 엔티티를 프록시 객체로 할당하며 실제 필요한 순간에 데이터베이스를 조회해서
  프록시 객체를 초기화하고 이후 프록시 객체를 통해 실제 엔티티에 접근할 수 있다.
- @ManyToOne 의 fetch 속성을 FetchType.LAZY 로 지정
- 실무에서는 대부분 지연로딩으로 설정하고 연관 엔티티를 함께 조회해야 할 경우에는 별도의 조회 쿼리, 메서드를 통해
조회하여 처리한다.
```java
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
```