## 엔티티 연관관계

### 단방향 연관관계
- 두 엔티티가 있을 때 한 쪽에서만 다른 엔티티를 참조하는 관계
- 단방향 연관관계는 객체관계에서만 존재하고 테이블 관계는 항상 양방향  
  ![img.png](img/relation.png)  
  - Member 객체는 team 필드로 Team 객체를 참조하지만 Team 객체는 member 를 참조하지 않는 상태

### 양방향 연관관계
- 두 엔티티가 있을 때 양쪽에서 서로 반대편의 엔티티를 참조하는 관계  
  ![img.png](img/1-on-n.png)

## 객체 관계 매핑
### @ManyToOne (N : 1)
![img_1.png](img/n-on-1.png)  
- 참조하는 엔티티와 N : 1 관계일 때 사용하는 애너테이션
  ```java
  @Entity
  public class Member {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
  
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
  
    private void setTeam(Team team) {
      this.team = team;
    }
  }
  
  @Entity
  public class Team {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;
  }
  ```
- @ManyToOne 의 프로퍼티
  - fetch
    - FetchType.LAZY
      - 참조하는 엔티티 데이터 조회시 지연로딩을 적용한다.
    - FetchType.EAGER
      - 참조하는 엔티티에 즉시로딩을 적용한다. 엔티티 데이터 조회시에 참조 엔티티를 자동으로 추가로 조회한다.
      - 의도치않게 불필요한 참조 엔티티를 조회할 수 있으므로 EAGER 보다는 LAZY 로 설정하는 것을 권장한다.

### @OneToMany (1 : N)
- 참조하는 엔티티와 1 : N 관계일 때 사용하는 애너테이션
  ```java
  @Entity
  public class Member {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
  
    // 연관관계의 주인. 연관된 team 을 등록, 변경, 삭제할 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
  
    private void setTeam(Team team) {
      this.team = team;
    }
  }
  
  @Entity
  public class Team {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;
  
    // 연관관계의 주인이 아닌 쪽은 읽기만 가능
    @OneToMany(mappedBy = "team")   // 연관관계의 주인을 Member.team 으로 지정
    private List<Member> members = new ArrayList<>();
  }
  ```
  
- @OneToMany 의 프로퍼티
  - mappedBy
    - 연관관계의 주인을 정하는 속성
    - 연관관계의 주인이 아닌 쪽은 읽기만 할 수 있다.

### @OneToOne (1 : 1)
- 참조하는 엔티티와 1 : 1 관계일 때 사용하는 애너테이션
    ```java
    @Entity
    public class Member {
  
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String username;
  
        @OneToOne
        private Locker locker;
  
        public void setLocker(Locker locker) {
            this.locker = locker;
        }
    }
  
    @Entity
    public class Locker {
  
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private String name;
  
        // 양방향 연관관계 시에는 연관관계의 주인을 Member.locker 로 지정
        // 해당 필드는 읽기만 가능
        @OneToOne(mappedBy = "locker")
        private Member member;
  
    }
  ```

### 순수한 객체를 고려한 양방향 연관관계
  - 객체 관점에서 양방향 연관관계 시에는 양쪽 방향에 모두 값을 입력해주는 것이 안전함
  - 양방향 연관관계시 데이터의 정합성을 위해 연관관계 편의 메서드를 작성한다.
      ```java
      @Entity
      @Getter
      public class Member {

          @Id
          @GeneratedValue(strategy = GenerationType.AUTO)
          private Long id;
          private String username;

          @ManyToOne(fetch = FetchType.LAZY)
          private Team team;

          // 연관관계 편의 메서드
          private void setTeam(Team team) {
              // 기존에 다른 팀에 속해있었다면 그 관계를 제거
              if (this.team != null) {
                  this.team.getMembers().remove(this);
              }
  
              this.team = team;
              team.getMembers().add(this);
          }
      }

      @Entity
      @Getter
      public class Team {

          @Id
          @GeneratedValue(strategy = GenerationType.AUTO)
          private Long id;
          private String teamName;

          @OneToMany(mappedBy = "team")
          private List<Member> members = new ArrayList<>();
      }
      ```