package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.UserIdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class LoggingItemWriter implements ItemWriter<UserIdDto> {

    @Override
    public void write(List<? extends UserIdDto> dtoList) throws Exception {
        log.info("Data written: " + dtoList);
    }

}
