package ruby.jpalearn.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer price;
    @Embedded
    private Address address;
}
