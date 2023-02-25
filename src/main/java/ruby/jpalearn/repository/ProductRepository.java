package ruby.jpalearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruby.jpalearn.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
