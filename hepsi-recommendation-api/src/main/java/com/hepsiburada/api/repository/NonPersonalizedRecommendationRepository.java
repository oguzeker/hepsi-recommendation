package com.hepsiburada.api.repository;

import com.hepsiburada.api.entity.NonPersonalizedRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NonPersonalizedRecommendationRepository extends JpaRepository<NonPersonalizedRecommendation, Long> {
}
