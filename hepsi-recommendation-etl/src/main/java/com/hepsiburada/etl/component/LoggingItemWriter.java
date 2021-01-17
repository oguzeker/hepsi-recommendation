package com.hepsiburada.etl.component;

import com.hepsiburada.etl.configuration.JdbcPaginationJobConfig;
import com.hepsiburada.etl.model.UserIdDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LoggingItemWriter implements ItemWriter<UserIdDto> {

    private UserIdDto userIdDto;
    private ItemWriter<UserIdDto> jdbcBatchItemWriter;

    @Override
    public void write(List<? extends UserIdDto> dtoList) throws Exception {
        userIdDto.setUserId(dtoList.get(0).getUserId());
        log.info("Writing data: {}", dtoList);

        jdbcBatchItemWriter.write(dtoList);

//        System.out.println("  >>>>  >>>> " + userIdDto.getUserId());
    }

//    @Override
//    public void write(List<? extends StudentDTO> list, List<String> list2) throws Exception {
//        list2.addAll(list);
//        LOGGER.info("Writing students: {}", list);
//    }

}
