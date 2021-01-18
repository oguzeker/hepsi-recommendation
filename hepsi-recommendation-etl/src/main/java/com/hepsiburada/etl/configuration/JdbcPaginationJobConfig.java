package com.hepsiburada.etl.configuration;

import com.hepsiburada.etl.component.CustomReader;
import com.hepsiburada.etl.component.LoggingItemWriter;
import com.hepsiburada.etl.configuration.properties.ApplicationProperties;
import com.hepsiburada.etl.model.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
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
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class JdbcPaginationJobConfig {

    private static final String STATEMENT_DELETE = "DELETE FROM user_ids";
    private static final String STATEMENT_INSERT = "insert into user_ids (user_id) values (:userId)";
    private static final String SELECT_CLAUSE = "SELECT distinct(user_id)";
    private static final String FROM_CLAUSE = "FROM orders";
    private static final String SORT_CONFIGURATION = "user_id";

    private static final String JDBC_PAGINATION_JOB = "jdbcPaginationJob";
    private static final String JDBC_PAGINATION_STEP = "jdbcPaginationStep";
    private static final String JDBC_PAGING_ITEM_READER = "pagingItemReader";

    private final ApplicationProperties applicationProperties;

    @Bean
    public Job jdbcPaginationJob(Step jdbcPaginationStep,
                                 JobBuilderFactory jobBuilderFactory) {
        Job jdbcPaginationJob = jobBuilderFactory.get(JDBC_PAGINATION_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(jdbcPaginationStep)
                .end()
                .build();
        return jdbcPaginationJob;
    }

    @Bean
    public Step jdbcPaginationStep(StepBuilderFactory stepBuilderFactory,
                                   PlatformTransactionManager platformTransactionManager,
                                   @Qualifier("jdbcPaginationTasklet") Tasklet jdbcPaginationTasklet,
                                   CustomReader jdbcPagingItemReader,
                                   @Qualifier("jdbcPagingItemWriter") ItemWriter<UserIdDto> jdbcPagingItemWriter,
                                   @Qualifier("loggingItemWriter") ItemWriter<UserIdDto> loggingItemWriter) {
        return stepBuilderFactory
                .get(JDBC_PAGINATION_STEP)
                .<UserIdDto, UserIdDto>chunk(1)
                .reader(jdbcPagingItemReader)
                .writer(loggingItemWriter)
//                .transactionManager(platformTransactionManager)
//                .tasklet(jdbcPaginationTasklet)
                .build();
    }

    @Bean
    public Tasklet jdbcPaginationTasklet(@Qualifier("deleteFromTableTasklet") Tasklet deleteFromTableTasklet,
                                         CustomReader jdbcPagingItemReader,
                                         ItemWriter<UserIdDto> jdbcPagingItemWriter) {
        return (contribution, chunkContext) -> {
            deleteFromTableTasklet.execute(contribution, chunkContext);

            Optional<UserIdDto> readItem = Optional.ofNullable(jdbcPagingItemReader.read());

            if (readItem.isPresent()) {
                //Thread.sleep(1000);

                jdbcPagingItemWriter.write(Collections.singletonList(readItem.get()));

                // ?? bakalım böyle çalışacak mı. items.clear();

                return RepeatStatus.CONTINUABLE;
            }

//            ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
//            executionContext.putLong("current.index", 0L);
//            jdbcPagingItemReader.update(executionContext);
            jdbcPagingItemReader.setCurrentItemCount(0);
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet deleteFromTableTasklet(JdbcTemplate jdbcTemplate) {
        return (contribution, chunkContext) -> {
            jdbcTemplate.execute(STATEMENT_DELETE);
            return RepeatStatus.FINISHED;
        };
    }

//    @Bean
//    public JdbcPagingItemReader<UserIdDto> jdbcPagingItemReader(DataSource dataSource,
//                                                                PagingQueryProvider queryProvider) {
//        return new JdbcPagingItemReaderBuilder<UserIdDto>()
//                .name(JDBC_PAGING_ITEM_READER)
//                .dataSource(dataSource)
//                .pageSize(applicationProperties.getPageSize())
//                .queryProvider(queryProvider)
//                .rowMapper(new BeanPropertyRowMapper<>(UserIdDto.class))
//                .build();
//    }

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
    public CustomReader jdbcPagingItemReader(DataSource dataSource,
                                                        PagingQueryProvider queryProvider,
                                                        ItemWriter<UserIdDto> jdbcPagingItemWriter,
                                                        JdbcTemplate jdbcTemplate) {
        CustomReader reader = new CustomReader(jdbcPagingItemWriter, jdbcTemplate);

        reader.setName(JDBC_PAGING_ITEM_READER);
        reader.setDataSource(dataSource);
        reader.setPageSize(applicationProperties.getPageSize());
        reader.setQueryProvider(queryProvider);
        reader.setRowMapper(new BeanPropertyRowMapper<>(UserIdDto.class));

        return reader;
    }

}
