package org.ecom.repository;

import java.util.Optional;
import org.ecom.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
	@Query("SELECT o FROM OrderDetails o WHERE o.deleted_at IS NULL")
	Page<OrderDetails> findAllActiveOrderDetails(Pageable pageable);

	@Query("SELECT o FROM OrderDetails o WHERE o.id = ?1 AND o.deleted_at IS NULL")
	Optional<OrderDetails> findActiveOrderDetailsById(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE OrderDetails o SET o.deleted_at = CURRENT_TIMESTAMP WHERE o.id = :id AND o.deleted_at IS NULL")
	void softDeleteOrderDetailsById(@Param("id") Long id);

}
