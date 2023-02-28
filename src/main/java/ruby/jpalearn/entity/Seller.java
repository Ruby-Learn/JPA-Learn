package ruby.jpalearn.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seller {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
}
