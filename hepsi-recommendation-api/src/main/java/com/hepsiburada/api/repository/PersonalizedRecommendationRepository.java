package com.hepsiburada.api.repository;

import com.hepsiburada.api.entity.PersonalizedRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalizedRecommendationRepository extends JpaRepository<PersonalizedRecommendation, Long> {

    List<PersonalizedRecommendation> findByUserId(String userId);

}
