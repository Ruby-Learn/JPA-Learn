package ruby.jpalearn.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;
    private String street;
}
