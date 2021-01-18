package com.hepsiburada.etl.component;

import com.hepsiburada.etl.model.UserIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CustomReader extends JdbcPagingItemReader<UserIdDto> {

    private final ItemWriter<UserIdDto> jdbcPagingItemWriter;
    private final JdbcTemplate jdbcTemplate;
    private final ResettableCountDownLatch resettableCountDownLatch;

    public UserIdDto read() throws Exception {
        UserIdDto readItem = null;

//        if (!lockingComponent.isLocked()) {

            Optional<UserIdDto> readItemOptional = Optional.ofNullable(super.read());

            if (readItemOptional.isPresent()) {

                jdbcTemplate.update("DELETE FROM USER_IDS");

                readItem = readItemOptional.get();
                log.info("Writing..." + readItem);
//                Thread.sleep(1000);
                resettableCountDownLatch.await();
                jdbcPagingItemWriter.write(Collections.singletonList(readItem));

            }
//        }
        return readItem;
    }

}
