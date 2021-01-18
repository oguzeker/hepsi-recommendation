package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.UserIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.Optional;
import com.hepsiburada.etl.model.UserIdDto;

@Slf4j
@RequiredArgsConstructor
public class CustomReader extends JdbcPagingItemReader<UserIdDto> {

    private final ItemWriter<UserIdDto> jdbcPagingItemWriter;
    private final JdbcTemplate jdbcTemplate;


    public UserIdDto read() throws Exception {


        Optional<UserIdDto> readItemOptional = Optional.ofNullable(super.read());
        UserIdDto readItem = null;

        if (readItemOptional.isPresent()) {
            jdbcTemplate.update("DELETE FROM USER_IDS");

            readItem = readItemOptional.get();
            log.info("Writing..." + readItem);notify();
            jdbcPagingItemWriter.write(Collections.singletonList(readItem));
        }

        return readItem;
    }

}
