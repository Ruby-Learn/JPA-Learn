package ruby.jpalearn.strategy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MOVIE")
public class Movie extends Item {

    private String director;
    private String actor;
}

