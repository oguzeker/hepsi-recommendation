package com.hepsiburada.etl.configuration;

import com.hepsiburada.etl.component.LoggingItemReader;
import com.hepsiburada.etl.component.LoggingItemWriter;
import com.hepsiburada.etl.component.MyTasklet;
import com.hepsiburada.etl.component.StepItemReadListener;
import com.hepsiburada.etl.configuration.properties.ApplicationProperties;
import com.hepsiburada.etl.model.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JdbcPaginationJobConfig {

    public static final UserIdDto USER_ID_DTO = new UserIdDto();
    private final ApplicationProperties applicationProperties;

    @Bean
    public JdbcPagingItemReader<UserIdDto> jdbcPagingItemReader(DataSource dataSource, PagingQueryProvider queryProvider) {
        //JdbcPagingItemReader a;a.
        return new JdbcPagingItemReaderBuilder<UserIdDto>()
                .name("pagingItemReader")
                .dataSource(dataSource)
                .pageSize(applicationProperties.getPageSize())
                .queryProvider(queryProvider)
                .rowMapper(new BeanPropertyRowMapper<>(UserIdDto.class))
                .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(DataSource dataSource) {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();

        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("SELECT distinct(user_id)");
        queryProvider.setFromClause("FROM orders");
        queryProvider.setSortKeys(sortByUserIdAsc());

        return queryProvider;
    }

    private Map<String, Order> sortByUserIdAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("user_id", Order.ASCENDING);
        return sortConfiguration;
    }

    @Bean
    public ItemWriter<UserIdDto> jdbcPagingItemWriter(ItemWriter<UserIdDto> jdbcBatchItemWriter) {
        return new LoggingItemWriter(USER_ID_DTO, jdbcBatchItemWriter);
    }

    @Bean
    public ItemWriter<UserIdDto> jdbcBatchItemWriter(DataSource dataSource) {
        JdbcBatchItemWriter<UserIdDto> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into user_ids (user_id) values (:userId)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Tasklet deleteFromTableTasklet(JdbcTemplate jdbcTemplate) {
        return (contribution, chunkContext) -> {
            jdbcTemplate.execute(DELETE_SCRIPT);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step deleteFromTable(StepBuilderFactory stepBuilderFactory, PlatformTransactionManager platformTransactionManager,
                           Tasklet deleteFromTableTasklet) {
        return stepBuilderFactory
                .get("deleteFromTable")
                .transactionManager(platformTransactionManager)
                .tasklet(deleteFromTableTasklet)
                .build();
    }

    private static final String DELETE_SCRIPT = "DELETE FROM user_ids";

    @Bean
    public Step jdbcPaginationStep(@Qualifier("jdbcPagingItemReader") JdbcPagingItemReader<UserIdDto> reader,
                                   @Qualifier("jdbcPagingItemWriter") ItemWriter<UserIdDto> writer,
                                   StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("jdbcPaginationStep")
                .<UserIdDto, UserIdDto>chunk(1)
                .listener(new StepItemReadListener())
                .reader(new LoggingItemReader(reader))
                .writer(writer)
                .build();
    }

//    @Bean
//    public Job jdbcPaginationJob(@Qualifier("jdbcPaginationStep") Step jdbcPaginationStep,
//                                 Step deleteFromTable,
//                                 JobBuilderFactory jobBuilderFactory) {
//        Job jdbcPaginationJob = jobBuilderFactory.get("jdbcPaginationJob")
//                .incrementer(new RunIdIncrementer())
//                .start(deleteFromTable)
//                .next(jdbcPaginationStep)
//                .next(deleteFromTable)
//                .build();
//        return jdbcPaginationJob;
//    }

    @Bean
    public Job jdbcPaginationJob(Step asd,
                                 Step deleteFromTable,
                                 JobBuilderFactory jobBuilderFactory) {
        Job jdbcPaginationJob = jobBuilderFactory.get("jdbcPaginationJob")
                .incrementer(new RunIdIncrementer())

                .start(deleteFromTable)
                .next(asd)
                .next(deleteFromTable)
                .build();
        return jdbcPaginationJob;
    }

    @Bean
    public Step asd(StepBuilderFactory stepBuilderFactory, PlatformTransactionManager platformTransactionManager, Tasklet deleteFromTableTasklet,
                    @Qualifier("jdbcPagingItemReader") JdbcPagingItemReader<UserIdDto> reader,
                    @Qualifier("jdbcPagingItemWriter") ItemWriter<UserIdDto> writer) {
        return stepBuilderFactory
                .get("MyTasklet")
                .transactionManager(platformTransactionManager)
                .tasklet(new MyTasklet(deleteFromTableTasklet, reader, writer))
//                .tasklet(new MyTasklet(deleteFromTableTasklet))
                .build();
    }
}
