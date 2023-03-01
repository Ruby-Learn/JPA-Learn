package ruby.jpalearn.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ruby.jpalearn.entity.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> findProducts() {
        return jpaQueryFactory
                .selectFrom(QProduct.product)
                .orderBy(QProduct.product.id.desc())
                .offset(30)
                .limit(10)
                .fetch();
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(QProduct.product)
                        .where(QProduct.product.name.contains(name))
                        .fetchOne()
        );
    }

    @Override
    public List<Integer> getSumPriceByTypes() {
         return jpaQueryFactory
                .select(QProduct.product.price.sum())
                .from(QProduct.product)
                .groupBy(QProduct.product.productType)
                .having(QProduct.product.price.sum().gt(100000))
                .fetch();
    }

    @Override
    public List<Product> findProductsAndSellers() {
        return jpaQueryFactory
                .selectFrom(QProduct.product)
                .leftJoin(QProduct.product.seller, QSeller.seller).fetchJoin()
                .fetch();
    }

    @Override
    public List<Product> findProductsByAvgPrice() {
        return jpaQueryFactory
                .selectFrom(QProduct.product)
                .where(
                        QProduct.product.price.gt(
                                JPAExpressions
                                        .select(QProduct.product.price.avg())
                                        .from(QProduct.product)
                                        .fetchOne()
                        )
                )
                .fetch();
    }

    @Override
    public List<String> findProductNames() {
        return jpaQueryFactory
                .select(QProduct.product.name).distinct()
                .from(QProduct.product)
                .fetch();
    }

    @Override
    public List<ProductDto> findProductsNameAndSellerEmail() {
        return jpaQueryFactory
                .select(new QProductDto(QProduct.product.name, QSeller.seller.email))
                .from(QProduct.product)
                .leftJoin(QProduct.product.seller, QSeller.seller)
                .fetch();
    }

    @Override
    @Transactional
    public long bulkUpdateProductPrice() {
        return jpaQueryFactory
                .update(QProduct.product)
                .set(QProduct.product.price, QProduct.product.price.multiply(1.1))
                .where(QProduct.product.price.lt(10000))
                .execute();
    }
}
