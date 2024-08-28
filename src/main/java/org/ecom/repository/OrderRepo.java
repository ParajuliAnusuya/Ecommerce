package org.ecom.repository;

import org.ecom.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.deleted_at IS NULL")
	Page<org.ecom.entity.Order> findAllActiveOrders(Pageable pageable);

	@Query("SELECT o FROM Order o WHERE o.id = ?1 AND o.deleted_at IS NULL")
	Optional<Order> findActiveOrderById(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Order o SET o.deleted_at = CURRENT_TIMESTAMP WHERE o.id = ?1 AND o.deleted_at IS NULL")
	void softDeleteOrderById(@Param("id") Long id);

}
