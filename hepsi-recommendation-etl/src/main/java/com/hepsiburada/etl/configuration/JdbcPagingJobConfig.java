package com.hepsiburada.etl.configuration;

import com.hepsiburada.etl.component.CustomItemReader;
import com.hepsiburada.etl.component.LoggingItemWriter;
import com.hepsiburada.etl.component.ResettableCountDownLatch;
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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JdbcPagingJobConfig {

    public static final String STATEMENT_DELETE = "DELETE FROM user_ids";
    private static final String STATEMENT_INSERT = "INSERT INTO user_ids (user_id) values (:userId)";
    private static final String SELECT_CLAUSE = "SELECT DISTINCT(user_id)";
    private static final String FROM_CLAUSE = "FROM orders";
    private static final String SORT_CONFIGURATION = "user_id";

    private static final String JDBC_PAGING_JOB = "jdbcPagingJob";
    private static final String JDBC_PAGING_STEP = "jdbcPagingStep";
    private static final String JDBC_PAGING_ITEM_READER = "pagingItemReader";

    private final ApplicationProperties applicationProperties;
    private final ResettableCountDownLatch resettableCountDownLatch;

    @Bean
    public Job jdbcPagingJob(Step jdbcPagingStep,
                                 JobBuilderFactory jobBuilderFactory) {
        Job jdbcPagingJob = jobBuilderFactory.get(JDBC_PAGING_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(jdbcPagingStep)
                .end()
                .build();
        return jdbcPagingJob;
    }

    @Bean
    public Step jdbcPagingStep(StepBuilderFactory stepBuilderFactory,
                               ItemReader<UserIdDto> jdbcPagingItemReader,
                               @Qualifier("loggingItemWriter") ItemWriter<UserIdDto> loggingItemWriter) {
        return stepBuilderFactory
                .get(JDBC_PAGING_STEP)
//                .listener(new StepItemReadListener())
                .<UserIdDto, UserIdDto>chunk(1)
                .reader(jdbcPagingItemReader)
                .writer(loggingItemWriter)
                .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(DataSource dataSource) {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();

        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause(SELECT_CLAUSE);
        queryProvider.setFromClause(FROM_CLAUSE);
        queryProvider.setSortKeys(sortByUserIdAsc());

        return queryProvider;
    }

    private Map<String, Order> sortByUserIdAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put(SORT_CONFIGURATION, Order.ASCENDING);
        return sortConfiguration;
    }

    @Bean
    public ItemWriter<UserIdDto> jdbcPagingItemWriter(DataSource dataSource) {
        JdbcBatchItemWriter<UserIdDto> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql(STATEMENT_INSERT);
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<UserIdDto> loggingItemWriter() {
        return new LoggingItemWriter();
    }

    @Bean
    public ItemReader<UserIdDto> jdbcPagingItemReader(DataSource dataSource,
                                                      PagingQueryProvider queryProvider,
                                                      ItemWriter<UserIdDto> jdbcPagingItemWriter,
                                                      JdbcTemplate jdbcTemplate) {
        CustomItemReader reader = new CustomItemReader(jdbcPagingItemWriter, jdbcTemplate, resettableCountDownLatch);

        reader.setName(JDBC_PAGING_ITEM_READER);
        reader.setDataSource(dataSource);
        reader.setPageSize(applicationProperties.getPageSize());
        reader.setQueryProvider(queryProvider);
        reader.setRowMapper(new BeanPropertyRowMapper<>(UserIdDto.class));

        return reader;
    }

}
