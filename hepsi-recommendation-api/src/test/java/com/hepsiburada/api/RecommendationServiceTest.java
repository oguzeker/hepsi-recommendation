package com.hepsiburada.api;

import com.hepsiburada.api.controller.response.RecommendationResponse;
import com.hepsiburada.api.entity.NonPersonalizedRecommendation;
import com.hepsiburada.api.entity.PersonalizedRecommendation;
import com.hepsiburada.api.model.RecommendationType;
import com.hepsiburada.api.properties.RecommendationProperties;
import com.hepsiburada.api.repository.NonPersonalizedRecommendationRepository;
import com.hepsiburada.api.repository.PersonalizedRecommendationRepository;
import com.hepsiburada.api.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static org.mockito.Mockito.*;

@CoreTest
public class RecommendationServiceTest extends TestBase {

    @InjectMocks
    RecommendationServiceImpl subject;
    @Mock
    PersonalizedRecommendationRepository personalizedRecommendationRepository;
    @Mock
    NonPersonalizedRecommendationRepository nonPersonalizedRecommendationRepository;
    @Mock
    RecommendationProperties recommendationProperties;

    @Test
    public void testGetRecommendationPersonalized() {
        PersonalizedRecommendation personalizedRecommendation = createPersonalizedRecommendation();

        NonPersonalizedRecommendation nonPersonalizedRecommendation = createNonPersonalizedRecommendation();

        when(recommendationProperties.getMinNumberOfProducts()).thenReturn(5);
        when(personalizedRecommendationRepository.findByUserId(anyString())).thenReturn(
                Collections.singletonList(personalizedRecommendation));

        RecommendationResponse recommendation = subject.getRecommendation(USER_ID);

        assert recommendation != null;
        assert recommendation.getProducts().isEmpty();
        assert recommendation.getUserId().equals(USER_ID);
        assert recommendation.getRecommendationType() == RecommendationType.PERSONALIZED;

        verify(recommendationProperties, times(1)).getMinNumberOfProducts();
        verify(personalizedRecommendationRepository, times(1)).findByUserId(anyString());
        verify(nonPersonalizedRecommendationRepository, never()).findAll();
    }

    @Test
    public void testGetRecommendationNonPersonalized() {
        NonPersonalizedRecommendation nonPersonalizedRecommendation = createNonPersonalizedRecommendation();

        when(recommendationProperties.getMinNumberOfProducts()).thenReturn(MIN_NUMBER_OF_PRODUCTS);
        when(personalizedRecommendationRepository.findByUserId(anyString())).thenReturn(
                Collections.emptyList());

        when(nonPersonalizedRecommendationRepository.findAll()).thenReturn(
                Collections.singletonList(nonPersonalizedRecommendation));

        RecommendationResponse recommendation = subject.getRecommendation(USER_ID);

        assert recommendation != null;
        assert recommendation.getProducts().isEmpty();
        assert recommendation.getUserId().equals(USER_ID);
        assert recommendation.getRecommendationType() == RecommendationType.NON_PERSONALIZED;

        verify(recommendationProperties, times(1)).getMinNumberOfProducts();
        verify(personalizedRecommendationRepository, times(1)).findByUserId(anyString());
        verify(nonPersonalizedRecommendationRepository, times(1)).findAll();
    }
}
