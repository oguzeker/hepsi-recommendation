package com.hepsiburada.etl.configuration;

import com.hepsiburada.etl.configuration.properties.ApplicationProperties;
import com.hepsiburada.etl.model.NonPersonalizedRecommendationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.text.MessageFormat;

@Configuration
@RequiredArgsConstructor
public class NonPersonalizedJobConfig {

    private static final String STATEMENT_INSERT =
            "   INSERT INTO non_personalized_recommendations "
            + " (row_label, row_num, sales_count, product_id, category_id) "
            + " VALUES(:rowLabel, :rowNum, :salesCount, :productId, :categoryId) "
            + " ON CONFLICT ON constraint uq_non_personalized_recommendations "
            + " DO UPDATE SET sales_count = EXCLUDED.sales_count, product_id = EXCLUDED.product_id, category_id = EXCLUDED.category_id ";
    private static final String QUERY_NON_PERSONALIZED_RECOMMENDATION =
            "   SELECT   row_number() OVER () AS row_num, result_set.* "
            + " FROM     ("
            + "   SELECT     count(1) sales_count, "
            + "              p.product_id, "
            + "              p.category_id "
            + "   FROM       orders o "
            + "   INNER JOIN order_items oi "
            + "   ON         o.order_id = oi.order_id "
            + "   INNER JOIN products p "
            + "   ON         oi.product_id = p.product_id "
            + "   GROUP BY   p.product_id, "
            + "              p.category_id "
            + "   ORDER BY   sales_count DESC,"
            + "              product_id DESC, "
            + "              category_id DESC "
            + "   LIMIT {0} "
            + " ) result_set ";

    private static final String READER_NAME = "nonPersonalizedItemReader";
    private static final String STEP_1_NAME = "nonPersonalizedStep";
    private static final String JOB_NAME = "nonPersonalizedJob";

    private final ApplicationProperties applicationProperties;

    @Bean
    public Job nonPersonalizedJob(Step nonPersonalizedStep,
                                  JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(nonPersonalizedStep)
                .end()
                .build();
    }

    @Bean
    public Step nonPersonalizedStep(ItemReader<NonPersonalizedRecommendationDto> nonPersonalizedItemReader,
                                    ItemWriter<NonPersonalizedRecommendationDto> nonPersonalizedItemWriter,
                                    StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get(STEP_1_NAME)
                .<NonPersonalizedRecommendationDto, NonPersonalizedRecommendationDto>chunk(
                        applicationProperties.getPersonalizedRecommendationsChunkSize())
                .reader(nonPersonalizedItemReader)
                .writer(nonPersonalizedItemWriter)
                .build();
    }

    @Bean
    public ItemReader<NonPersonalizedRecommendationDto> nonPersonalizedItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<NonPersonalizedRecommendationDto>()
                .name(READER_NAME)
                .dataSource(dataSource)
                .sql(MessageFormat.format(QUERY_NON_PERSONALIZED_RECOMMENDATION,
                        applicationProperties.getNonPersonalizedProductLimit()))
                .rowMapper(new BeanPropertyRowMapper<>(NonPersonalizedRecommendationDto.class))
                .build();
    }

    @Bean
    public ItemWriter<NonPersonalizedRecommendationDto> nonPersonalizedItemWriter(DataSource dataSource) {
        JdbcBatchItemWriter<NonPersonalizedRecommendationDto> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql(STATEMENT_INSERT);
        writer.setDataSource(dataSource);
        return writer;
    }

}
