package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.PersonalizedRecommendationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class CustomItemWriter extends JdbcBatchItemWriter<PersonalizedRecommendationDto> {

    public void write(final List<? extends PersonalizedRecommendationDto> items) throws Exception {
        super.write(items);
    }

}
