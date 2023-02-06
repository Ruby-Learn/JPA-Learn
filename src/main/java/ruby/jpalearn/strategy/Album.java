package ruby.jpalearn.strategy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ALBUM")
public class Album extends Item {

    private String artist;
}
