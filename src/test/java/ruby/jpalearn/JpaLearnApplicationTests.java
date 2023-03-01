package ruby.jpalearn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruby.jpalearn.repository.ProductRepository;

@SpringBootTest
class JpaLearnApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void testBulkUpdate() {
        productRepository.bulkUpdateProductPrice();
    }
}
