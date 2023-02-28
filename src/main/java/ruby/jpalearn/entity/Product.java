package ruby.jpalearn.entity;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne
    private Seller seller;
}
