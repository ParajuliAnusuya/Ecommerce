package org.ecom.repository;

import org.ecom.entity.UserWishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface UserWishListRepository extends JpaRepository<UserWishList, Long> {
	@Query("SELECT uw FROM UserWishList uw WHERE uw.user.id = :userId")
	Page<UserWishList> findByUserId(Long userId, Pageable pageable);

	@Query("SELECT uw FROM UserWishList uw WHERE uw.user.id = :userId AND uw.product.id = :productId")
	Page<UserWishList> findByUserIdAndProductId(Long userId, Long productId, Pageable pageable);

	Page<UserWishList> findByProductId(Long productId, Pageable pageable);

	@Modifying
	@Transactional
	@Query("DELETE FROM UserWishList uw WHERE uw.user.id = :userId AND uw.product.id = :productId")
	void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
