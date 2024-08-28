package org.ecom.repository;

import java.util.List;
import java.util.Optional;
import org.ecom.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT c FROM Category c WHERE c.deleted_at IS NULL")
	List<Category> viewAllCategory();

	@Query("SELECT c FROM Category c WHERE c.id = :id AND c.deleted_at IS NULL")
	Optional<Category> findById(@Param("id") Long id);

	Optional<Category> findByName(String name);

	// Find all categories ordered by name
	Page<Category> findAllByOrderByName(Pageable pageable);

	//Count the number of products in a category
	@Query("SELECT COUNT(p) FROM Products p WHERE p.category.id = :categoryId")
	Long countProductsInCategory(Long categoryId);

	@Modifying
	@Transactional
	@Query("UPDATE Category c SET c.deleted_at = CURRENT_TIMESTAMP WHERE c.id = :id AND c.deleted_at IS NULL")
	void softDeleteCategoryById(@Param("id") Long id);

	Page<Category> findAll(Pageable pageable);

}
