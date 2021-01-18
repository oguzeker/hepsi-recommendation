//package com.hepsiburada.etl.component;
//
////import com.hepsiburada.etl.dao.UserIdDao;
//import com.hepsiburada.etl.model.UserIdDto;
////import com.hepsiburada.etl.repository.UserIdsRepository;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
//import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class MyTasklet implements Tasklet {
//
//    private final Tasklet deleteFromTableTasklet;
//
////    private final ItemReader<UserIdDto> jdbcPagingItemReader;
////    @Autowired
////    private DataSource dataSource;
//    private final SqlPagingQueryProviderFactoryBean queryProvider;
//
//    private final ItemWriter<UserIdDto> jdbcPagingItemWriter;
//
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//
//        ItemReader<UserIdDto> jdbcPagingItemReader = jdbcPagingItemReader(hikariDataSource(), queryProvider);
//
//        deleteFromTableTasklet.execute(contribution, chunkContext);
//
//        Optional<UserIdDto> readItem = Optional.ofNullable(jdbcPagingItemReader.read());
//
//        if (readItem.isPresent()) {
//            //Thread.sleep(1000);
//
//            jdbcPagingItemWriter.write(Collections.singletonList(readItem.get()));
//
//            // ?? bakalım böyle çalışacak mı. items.clear();
//
//            return RepeatStatus.CONTINUABLE;
//        }
//
//        return RepeatStatus.FINISHED;
//    };
//
//    public JdbcPagingItemReader<UserIdDto> jdbcPagingItemReader(DataSource dataSource,
//                                                                SqlPagingQueryProviderFactoryBean queryProvider) throws Exception {
//        return new JdbcPagingItemReaderBuilder<UserIdDto>()
//                .name("pagingItemReader")
//                .dataSource(dataSource)
//                .pageSize(1)
//                .queryProvider(queryProvider.getObject())
//                .rowMapper(new BeanPropertyRowMapper<>(UserIdDto.class))
//                .build();
//    }
//
//    public HikariDataSource hikariDataSource() {
//        HikariConfig dataSourceConfig = new HikariConfig();
//        dataSourceConfig.setDriverClassName("org.postgresql.Driver");
//        dataSourceConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/data-db");
//        dataSourceConfig.setUsername("postgres");
//        dataSourceConfig.setPassword("123456");
//        dataSourceConfig.setAutoCommit(true);
//        return new HikariDataSource(dataSourceConfig);
//    }
//
//}
