package ruby.jpalearn.strategy;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")    // name 을 지정하지 않을 경우 DTYPE 으로 기본 값이 지정됨
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
