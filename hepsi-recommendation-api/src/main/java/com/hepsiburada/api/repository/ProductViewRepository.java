package com.hepsiburada.api.repository;

import com.hepsiburada.api.entity.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

    Page<ProductView> findByUserIdOrderByCreatedDate(String userId, Pageable paging);

    void deleteByUserIdAndProductId(String userId, String productId);

}
