package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.PersonalizedRecommendationDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@NoArgsConstructor
public class OguzLoggingItemWriter implements ItemWriter<PersonalizedRecommendationDto> {


    @Override
    public void write(List<? extends PersonalizedRecommendationDto> wrapper) throws Exception {
        log.info("YAZDIM ULAAAAAAAAAA !!! {}", wrapper);
    }

//    @Override
//    public void write(List<? extends StudentDTO> list, List<String> list2) throws Exception {
//        list2.addAll(list);
//        LOGGER.info("Writing students: {}", list);
//    }

}
