## Entity
- JPA 가 관리하는 클래스
- Relation 과 매핑되는 클래스

## Entity 연관 Annotation
### @Entity
- @Entity 를 붙여 테이블과 매핑할 클래스를 지정
- @Entity 의 속성
  - name
    - 엔티티의 이름을 지정
    - 값을 설정하지 않으면 클래스의 이름을 기본값으로 사용
- 엔티티의 주의사항
  - 엔티티는 파라미터가 없는 기본 생성자가 반드시 존재해야 한다.
    - 파라미터가 없는 public 또는 protected 생성자
  - final, enum, interface, inner 클래스는 엔티티로 지정할 수 없다.
  - Column 과 매핑할 필드에 final 을 사용해서는 안 된다.

### @Table
- 엔티티와 매핑할 테이블을 지정
- @Table 의 속성
  - name
    - 엔티티와 매핑할 테이블 이름을 지정
    - @Table 및 name 속성을 설정하지 않으면 엔티티의 이름을 기본값으로 사용
  - schema
    - schema 기능이 있는 데이터베이스에서 schema 를 매핑
    - DDL 생성 시에 유니크 제약 조건을 설정
      - 스키마 자동 생성 기능을 사용해서 DDL 을 만들 때에만 해당 설정이 적용됨

### @Id
- 기본키(PK)로 지정할 필드를 지정
- 기본키를 직접 할당시에는 @Id 만 사용
- 기본키를 자동 생성 전략을 통해 생성하려면 @GeneratedValue 를 통해 전략을 설정
  - @GeneratedValue 의 strategy 속성값을 설정하여 키 생성 전략을 설정
  - @GeneratedValue 의 키 생성 전략
    - GenerationType.IDENTITY
      - 기본 키 생성을 데이터베이스에 위임하는 전략
    - GenerationType.SEQUENCE
      - 데이터베이스의 시퀀스를 사용해서 기본키를 생성하는 전략
    - GenerationType.TABLE
      - 키 생성 전용 테이블을 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략
    - GenerationType.AUTO
      - 키 생성 전략의 기본값
      - 설정된 데이터베이스 방언에 따라 위의 전략 중 하나를 자동으로 선택
      - 데이터베이스를 변경해도 자동으로 설정해주기 때문에 코드를 수정할 필요가 없다는 장점이 있다.
        - 키 생성 전략이 확정되지 않은 개발 초기 단계나 프로토타입 개발 시 유용하게 사용할 수 있다.

### @Column
- 객체 필드를 테이블 컬럼에 매핑
- 엔티티의 필드는 기본적으로 @Column 을 생략해도 적용되며 추가 설정이 필요할때 @Column 을 사용한다.
- @Column 의 속성
  - name
    - 필드와 매핑할 테이블 컬럼의 이름을 지정
  - nullable
    - null 값 허용 여부를 설정
    - false 로 설정시 DDL 생성 시에 not null 제약조건을 추가한다.
  - unique
    - true 로 설정시 DDL 생성 시에 unique 제약 조건을 추가한다.
  - length
    - 문자 길이 제약조건. String 타입의 필드에만 적용된다.

### @Enumerated
- Enum 타입의 필드를 테이블 컬럼에 매핑할 때 사용
- @Enumerated 의 속성
  - value
    - EnumType.ORDINAL
      - enum 순서 값을 데이터베이스에 저장
      - 데이터베이스에 저장되는 데이터의 크기가 작다는 장점이 있지만 enum 클래스의 순서가 뒤바뀌면 저장되어 있던 데이터 자체가 변경되어 버리므로 권장하지 않음
    - EnumType.STRING
      - enum name 값을 데이터베이스에 저장

### @Temporal
- 날짜 타입을 매핑할 때 사용
- LocalDate, LocalDateTime 타입이 추가된 이후에는 @Temporal 을 사용하지 않아도 해당 타입으로 데이터베이스 날짜 타입을 구분할 수 있다.
- @Temporal 의 속성
  - value
    - TemporalType.DATE
      - 데이터베이스 date 타입과 매핑 (ex - 2023-01-07)
    - TemporalType.TIME
      - 데이터베이스 time 타입과 매핑 (ex - 15:06:33)
    - TemporalType.TIMESTAMP
      - 데이터베이스 timestamp 타입과 매핑 (ex - 2023-01-07 15:06:33)

### @Lob
- 데이터베이스 BLOB, CLOB 타입과 매핑할 때 사용
- 매핑하는 필드 타입이 문자면 CLOB 으로, 나머지는 BLOB 으로 매핑
  - CLOB 매핑
    - String, char[]
  - BLOB 매핑
    - byte[]

### @Transient
- 지정한 필드를 테이블 컬럼에 매핑하지 않을 때 사용
- 객체에 임시로 값을 보관하고 싶을 때 사용