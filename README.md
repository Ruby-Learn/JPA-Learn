## 즉시로딩 - Eager Loading
- 엔티티를 조회할 때 연관된 엔티티로 함께 조회
```java
@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;
}
```

## 지연로딩 - Lazy Loading
- 연관된 엔티티를 실제 사용하는 시점에 조회