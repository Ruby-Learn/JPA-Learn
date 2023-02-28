package ruby.jpalearn.repository;

import ruby.jpalearn.entity.Product;
import ruby.jpalearn.entity.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    List<Product> findProducts();

    Optional<Product> findByName(String name);

    List<Integer> getSumPriceByTypes();

    List<Product> findProductsAndSellers();

    List<Product> findProductsByAvgPrice();

    List<String> findProductNames();

    List<ProductDto> findProductsNameAndSellerEmail();
}
