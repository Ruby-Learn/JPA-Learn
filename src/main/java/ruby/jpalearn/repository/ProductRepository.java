package ruby.jpalearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ruby.jpalearn.entity.Address;
import ruby.jpalearn.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p.address from Product p")
    List<Address> findAllAddress();

    @Query("select p.name from Product p")
    List<String> findAllNames();

    @Modifying
    @Transactional
    @Query("update Product p set p.price = p.price * 1.1 where p.address.city like '서울%'")
    int bulkUpdateProductPrice();
}
