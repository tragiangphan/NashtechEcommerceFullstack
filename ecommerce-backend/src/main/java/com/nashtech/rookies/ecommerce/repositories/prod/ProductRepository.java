package com.nashtech.rookies.ecommerce.repositories.prod;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookies.ecommerce.models.prods.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(Long id);
    boolean existsByProductName(String productName);
    Product findByProductName(String productName);

    @Query(value =
            "SELECT * " +
            "FROM products p " +
            "WHERE p.product_name " +
            "LIKE :productName",
            nativeQuery = true)
    Page<Product> findAllByProductNameLike(@Param("productName") String productName,
                                           Pageable pageable);

    @Query(value =
            "SELECT p.* " +
            "FROM products p " +
            "JOIN categories c " +
            "ON p.category_id = c.id " +
            "WHERE c.category_name LIKE :categoryName",
            nativeQuery = true)
    Page<Product> findAllByCategoryName(@Param("categoryName") String categoryName,
                                        Pageable pageable);

    @Query(value = "SELECT * FROM products p WHERE p.price > :minPrice AND p.price < :maxPrice", nativeQuery = true)
    Page<Product> findAllByPriceGreaterThanAndPriceLessThan(@Param("maxPrice") Long maxPrice,
                                                            @Param("minPrice") Long minPrice,
                                                            Pageable pageable);
}
