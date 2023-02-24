## JPQL - Java Persistence Query Language
- 엔티티 객체를 조회하는 객체지향 쿼리
  - JPQL 은 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
- SQL 과 문법이 비슷하며 ANSI 표준 SQL 이 제공하는 기능을 유사하게 지원
- JPQL 은 SQL 을 추상화해서 특정 데이터베이스에 의존하지 않는다.
```java
String jpql = "select m from Member as m where m.username = 'kim'";
List<Member> members = em.createQuery(jpql, Member.class).getResultList();
```
```sql
-- 실행된 JPQL
select  m
from    Member as m
where   m.username = 'kim'

-- 실행된 SQL
select  member.id as id,
        member.age as age,
        member.team_id as team,
        member.name as name
from    Member member
where   member.name = 'kim'
```

### Projection
- Select 조회시 조회할 대상을 지정하여 조회하는 것
  - 엔티티 프로젝션
    - 엔티티를 대상으로 지정하여 조회할 경우 조회한 엔티티는 영속성 컨텍스트에서 관리된다.
    - 엔티티를 조회할 경우에는 스프링 데이터 JPA 를 활용하므로 잘 사용되지는 않는다.
  - 임베디드 타입 프로젝션
    - 엔티티 프로젝션과 비슷하게 임베디드 타입으로 조회할 수 있다.
      - 임베디드 타입에 매칭되는 테이블이 없으므로 임베디드 타입을 사용하는 엔티티를 기준, from 절에 명시하여 조회한다.
      - 임베디드 타입은 엔티티 타입이 아니므로 조회결과는 영속성 컨텍스트에서 관리되지 않는다.
      ```java
      public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("select p.address from Product p")
        List<Address> findAllAddress();
      }
      ```
  - 스칼라 타입 프로젝션
    - 숫자, 문자, 날짜와 같은 기본 데이터 타입의 데이터를 조회
      ```java
      public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("select p.name from Product p")
        List<String> findAllNames();
      }
      ```
  - 여러 값 조회 (DTO 조회)
    - 꼭 필요한 데이터들만 선택하여 조회할 때 해당 데이터들을 묶은 DTO 를 통해 조회
      ```java
      public interface ArticleRepository extends JpaRepository<Article, Long> {

        Optional<Article> findByIdAndActiveTrue(Long id);
        Optional<Article> findByIdAndWriterAndActiveTrue(Long id, Account writer);

        @Query(
          value = 
          "SELECT a.id as articleId, a.title, a.content, w.nickname, w.account_type as accountType, f1.favoriteCount, " +
          "CASE WHEN f2.account_id IS NOT NULL THEN true ELSE false END as isMyFavorite " +
          "FROM Article as a " +
          "LEFT JOIN Account as w ON a.writer_id = w.id " +
          "LEFT JOIN (  " +
                            "SELECT article_id, COUNT(article_id) as favoriteCount " +
                            "FROM Favorite " +
                            "GROUP BY article_id) as f1 " +
          "ON a.id = f1.article_id " +
          "LEFT JOIN (  " +
                            "SELECT article_id, account_id " +
                            "FROM Favorite " +
                            "WHERE account_id = :accountId ) as f2 " +
          "ON a.id = f2.article_id " +
          "WHERE a.active = true " +
          "AND (a.title LIKE %:inputText% OR a.content LIKE %:inputText%) " +
          "ORDER BY a.id DESC",
          nativeQuery = true
        )
        List<ArticleProjection> findBySearch(@Param("inputText") String inputText, @Param("accountId") Long accountId);
      }
      ```