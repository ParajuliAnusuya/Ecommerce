package org.ecom.repository;

import org.ecom.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Products, Long> {

	@Query("SELECT p FROM Products p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')) AND p.deleted_at IS NULL")
	Page<Products> findByProductName(@Param("name") String name, Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.category.id = :categoryId AND p.deleted_at IS NULL")
	Page<Products> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.rate > :rate AND p.deleted_at IS NULL")
	Page<Products> findByRateGreaterThan(@Param("rate") Double rate, Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.rate < :rate AND p.deleted_at IS NULL")
	Page<Products> findByRateLessThan(@Param("rate") Double rate, Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.availableInStock > 0 AND p.deleted_at IS NULL")
	Page<Products> findAvailableProducts(Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.rate BETWEEN :minRate AND :maxRate AND p.deleted_at IS NULL")
	Page<Products> findByRateBetween(@Param("minRate") Double minRate, @Param("maxRate") Double maxRate, Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.deleted_at IS NULL")
	Page<Products> findAllActiveProducts(Pageable pageable);

	@Query("SELECT p FROM Products p WHERE p.id = :id AND p.deleted_at IS NULL")
	Products findActiveProductById(@Param("id") Long id);
}
