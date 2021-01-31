package com.hepsiburada.etl.configuration;

import com.hepsiburada.etl.component.ResettableCountDownLatch;
import com.hepsiburada.etl.configuration.properties.ApplicationProperties;
import com.hepsiburada.etl.model.PersonalizedRecommendationDto;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.text.MessageFormat;

@Configuration
@RequiredArgsConstructor
public class DatabaseCursorJobConfig {

    public static final String STATEMENT_DELETE =
            "DELETE FROM personalized_recommendations WHERE user_id = (SELECT user_id FROM user_ids)";
    private static final String STATEMENT_INSERT =
            "   INSERT INTO personalized_recommendations "
            + " (user_id, row_num, sales_count, product_id, category_id) "
            + " VALUES((SELECT user_id FROM user_ids), :rowNum, :salesCount, :productId, :categoryId) "
            + " ON CONFLICT ON constraint uq_personalized_recommendations "
            + " DO UPDATE SET sales_count = EXCLUDED.sales_count, product_id = EXCLUDED.product_id, category_id = EXCLUDED.category_id ";
    private static final String QUERY_PERSONALIZED_RECOMMENDATION =
            "   SELECT row_num, _sales_count as sales_count, product_id, category_id "
            + " FROM   ( "
            + "         SELECT   row_number() OVER (partition BY category_id ORDER BY category_id ASC, _sales_count DESC) AS row_num, "
            + "         result_set.* "
            + "                 FROM     ( "
            + "                         SELECT     count(1) _sales_count, "
            + "                         p.product_id, "
            + "                         p.category_id "
            + "                         FROM       orders o "
            + "                         INNER JOIN order_items oi "
            + "                         ON         o.order_id = oi.order_id "
            + "                         INNER JOIN products p "
            + "                         ON         oi.product_id = p.product_id "
            + "                         WHERE      p.category_id IN "
            + "                                 ( "
            + "                                         SELECT categories.category_id "
            + "                                         FROM   ( "
            + "                                                 SELECT     count(1) _order_count, "
            + "                                                 p.category_id "
            + "                                                 FROM       orders o "
            + "                                                 INNER JOIN order_items oi "
            + "                                                 ON         o.order_id = oi.order_id "
            + "                                                 INNER JOIN products p "
            + "                                                 ON         oi.product_id = p.product_id "
            + "                                                 WHERE      o.user_id = ( "
            + "                                                         SELECT u.user_id "
            + "                                                         FROM   user_ids u "
            + "                                                 ) "
            + "                                                 GROUP BY   p.category_id "
            + "                                                 ORDER BY   _order_count DESC, "
            + "                                                 category_id DESC "
            + "                                         ) categories LIMIT {0} "
            + "                                 ) "
            + "                         GROUP BY   p.product_id, "
            + "                         p.category_id "
            + "                 ) result_set "
            + " ) partitioned_result_set "
            + " WHERE  partitioned_result_set.row_num <= {1} ";
    public static final String DATABASE_CURSOR_ITEM_READER = "databaseCursorItemReader";
    public static final String DATABASE_CURSOR_STEP = "databaseCursorStep";
    public static final String DATABASE_CURSOR_JOB = "databaseCursorJob";

    private final ApplicationProperties applicationProperties;
    private final ResettableCountDownLatch resettableCountDownLatch;

    @Bean
    public Job databaseCursorJob(@Qualifier("databaseCursorStep") Step databaseCursorStep,
                                 JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get(DATABASE_CURSOR_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(databaseCursorStep)
                .end()
                .build();
    }

    @Bean
    public Step databaseCursorStep(ItemReader<PersonalizedRecommendationDto> reader,
                                   ItemWriter<PersonalizedRecommendationDto> writer,
                                   StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get(DATABASE_CURSOR_STEP)
                .<PersonalizedRecommendationDto, PersonalizedRecommendationDto>chunk(
                        applicationProperties.getPersonalizedRecommendationsChunkSize())
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<PersonalizedRecommendationDto> databaseCursorItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<PersonalizedRecommendationDto>()
                .name(DATABASE_CURSOR_ITEM_READER)
                .dataSource(dataSource)
                .sql(MessageFormat.format(QUERY_PERSONALIZED_RECOMMENDATION,
                        applicationProperties.getUserPreferredCategoryLimit(),
                        applicationProperties.getCategoryProductLimit()))
                .rowMapper(new BeanPropertyRowMapper<>(PersonalizedRecommendationDto.class))
                .build();
    }

    @Bean
    public ItemWriter<PersonalizedRecommendationDto> databaseCursorItemWriter(DataSource dataSource) {
        JdbcBatchItemWriter<PersonalizedRecommendationDto> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql(STATEMENT_INSERT);
        writer.setDataSource(dataSource);
        return writer;
    }

}
