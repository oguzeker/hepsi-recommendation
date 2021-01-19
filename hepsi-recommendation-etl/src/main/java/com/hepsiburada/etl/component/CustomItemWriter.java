package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.PersonalizedRecommendationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import java.util.List;

//@Slf4j
@RequiredArgsConstructor
public class CustomItemWriter extends JdbcBatchItemWriter<PersonalizedRecommendationDto> {

    private final ResettableCountDownLatch resettableCountDownLatch;

    public void write(final List<? extends PersonalizedRecommendationDto> items) throws Exception {
        super.write(items);
//        resettableCountDownLatch.countDown();
//        resettableCountDownLatch.reset();
    }

}
