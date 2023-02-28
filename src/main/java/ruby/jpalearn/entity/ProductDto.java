package ruby.jpalearn.entity;


import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductDto {

    private String name;
    private String email;

    @QueryProjection
    public ProductDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
