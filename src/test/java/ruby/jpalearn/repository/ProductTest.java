package ruby.jpalearn.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruby.jpalearn.entity.Address;

import java.util.List;

@SpringBootTest
class ProductTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testFindAllAddress() {
        List<Address> allAddress = productRepository.findAllAddress();
    }

    @Test
    void testFindAllNames() {
        List<String> allNames = productRepository.findAllNames();
    }
}