package com.hepsiburada.etl.component;

import com.hepsiburada.etl.dao.UserIdDao;
import com.hepsiburada.etl.model.UserIdDto;
import com.hepsiburada.etl.repository.UserIdsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyTasklet implements Tasklet {

    private final Tasklet deleteFromTableTasklet;

    private final ItemReader<UserIdDto> jdbcPagingItemReader;

//    @Autowired
//    private ItemProcessor<List<UserIdDto>, List<UserIdDto>> processorSpringBeanName;

    private final ItemWriter<UserIdDto> jdbcPagingItemWriter;

//    @Autowired
//    private UserIdDao dao;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    private UserIdsRepository userIdsRepository;

//    public void deleteFromUserIds() {
//        jdbcTemplate.update("DELETE FROM user_ids");
//    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        //jdbcPagingItemReader.

        deleteFromTableTasklet.execute(contribution, chunkContext);
//        dao.deleteFromUserIds();
//        userIdsRepository.deleteAll();

        List<UserIdDto> items = new ArrayList<>();
        UserIdDto readItem = jdbcPagingItemReader.read();

        if(readItem != null) {
            Thread.sleep(2000);

            items.add(readItem);
            jdbcPagingItemWriter.write(items);

            items.clear();
//            deleteFromTableTasklet.execute(contribution, chunkContext);
//            readItem = jdbcPagingItemReader.read();
//        }

//        jdbcPagingItemWriter.write(processorSpringBeanName.process(items));


            return RepeatStatus.CONTINUABLE;
        }

        return RepeatStatus.FINISHED;
    }
}
