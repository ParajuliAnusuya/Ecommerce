package org.ecom.repository;

import org.ecom.entity.UserCartItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserCartItemsRepo extends JpaRepository<UserCartItems, Long> {

	@Query("SELECT uci FROM UserCartItems uci WHERE uci.user.id = :userId AND uci.deleted_at IS NULL")
	Page<UserCartItems> findByUserId(@Param("userId") Long userId, Pageable pageable);
	
	@Query("SELECT u FROM UserCartItems u WHERE u.id = :id")
    Page<UserCartItems> findUserCartItemsById(@Param("id") Long id, Pageable pageable);

	@Query("SELECT uci FROM UserCartItems uci WHERE uci.user.id = :userId AND uci.product.id = :productId AND uci.deleted_at IS NULL")
	List<UserCartItems> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

	@Query("SELECT SUM(uci.quantity) FROM UserCartItems uci WHERE uci.user.id = :userId AND uci.deleted_at IS NULL")
	Integer findTotalQuantityByUserId(@Param("userId") Long userId);

	@Query("SELECT uci FROM UserCartItems uci WHERE uci.user.id = :userId AND uci.isChecked = true AND uci.deleted_at IS NULL")
	List<UserCartItems> findCheckedItemsByUserId(@Param("userId") Long userId);

	@Query("SELECT uci FROM UserCartItems uci WHERE uci.product.id = :productId AND uci.deleted_at IS NULL")
	Page<UserCartItems> findByProductId(@Param("productId") Long productId, Pageable pageable);
}
