//package com.hepsiburada.etl.configuration;
//
//import com.hepsiburada.etl.component.LoggingItemWriter;
//import com.hepsiburada.etl.component.OguzLoggingItemWriter;
//import com.hepsiburada.etl.configuration.properties.ApplicationProperties;
//import com.hepsiburada.etl.model.RecommendationDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import javax.sql.DataSource;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//public class DatabaseCursorJobConfig {
//
//    private static final String SINGLE_QUOTE = "'";
////    private static final String QUERY_USER_PREFERRED_CATEGORIES =
////            "   SELECT DISTINCT( o.user_id ) "
////            + " FROM   orders o "
////            + " ORDER  BY user_id ASC ";
//    private static final String QUERY_PERSONALIZED_RECOMMENDATION =
//            "   SELECT * "
//            + " FROM   ( "
//            + "         SELECT   row_number() OVER (partition BY category_id ORDER BY category_id ASC, _sales_count DESC) AS row_num, "
//            + "         result_set.* "
//            + "                 FROM     ( "
//            + "                         SELECT     count(1) _sales_count, "
//            + "                         p.product_id, "
//            + "                         p.category_id "
//            + "                         FROM       orders o "
//            + "                         INNER JOIN order_items oi "
//            + "                         ON         o.order_id = oi.order_id "
//            + "                         INNER JOIN products p "
//            + "                         ON         oi.product_id = p.product_id "
//            + "                         WHERE      p.category_id IN "
//            + "                                 ( "
//            + "                                         SELECT categories.category_id "
//            + "                                         FROM   ( "
//            + "                                                 SELECT     count(1) _order_count, "
//            + "                                                 p.category_id "
//            + "                                                 FROM       orders o "
//            + "                                                 INNER JOIN order_items oi "
//            + "                                                 ON         o.order_id = oi.order_id "
//            + "                                                 INNER JOIN products p "
//            + "                                                 ON         oi.product_id = p.product_id "
//            + "                                                 WHERE      o.user_id IN ({2}) "
//            + "                                                 GROUP BY   p.category_id "
//            + "                                                 ORDER BY   _order_count DESC, "
//            + "                                                 category_id DESC "
//            + "                                         ) categories LIMIT {0} "
//            + "                                 ) "
//            + "                         GROUP BY   p.product_id, "
//            + "                         p.category_id "
//            + "                         ORDER BY   _sales_count DESC, "
//            + "                         product_id DESC, "
//            + "                         category_id DESC "
//            + "                 ) result_set "
//            + " ) partitioned_result_set "
//            + " WHERE  partitioned_result_set.row_num <= {1} ";
//
//    public static final List<String> USER_IDS = new ArrayList();
//    private final ApplicationProperties applicationProperties;
//
//    @Bean
//    public ItemReader<RecommendationDto> databaseCursorItemReader(DataSource dataSource) {
//        System.out.println(" --------->>>>>>>");
//        System.out.println(" --------->>>>>>>");
//        System.out.println(" --------->>>>>>> " + JdbcPaginationJobConfig.USER_ID_DTO.getUserId());
//        System.out.println(" --------->>>>>>>");
//        System.out.println(" --------->>>>>>>");
//        return new JdbcCursorItemReaderBuilder<RecommendationDto>()
//                .name("cursorItemReader")
//                .dataSource(dataSource)
//                .sql(MessageFormat.format(QUERY_PERSONALIZED_RECOMMENDATION,
//                        applicationProperties.getUserPreferredCategoryLimit(),
//                        applicationProperties.getCategoryProductLimit(), new StringBuilder(SINGLE_QUOTE)
//                                //.append(String.join(SINGLE_QUOTE, Arrays.asList("user-282")))
//                                .append(String.join(SINGLE_QUOTE, JdbcPaginationJobConfig.USER_ID_DTO.getUserId()))
//                                .append(SINGLE_QUOTE)
//                                .toString()))
//                .rowMapper(new BeanPropertyRowMapper<>(RecommendationDto.class))
//                .build();
//    }
//
//    @Bean
//    public ItemWriter<RecommendationDto> databaseCursorItemWriter() {
//        return new OguzLoggingItemWriter();
//    }
//
//    @Bean
//    public Step databaseCursorStep(@Qualifier("databaseCursorItemReader") ItemReader<RecommendationDto> reader,
//                                   @Qualifier("databaseCursorItemWriter") ItemWriter<RecommendationDto> writer,
//                                   StepBuilderFactory stepBuilderFactory) {
//        return stepBuilderFactory.get("databaseCursorStep")
//                .<RecommendationDto, RecommendationDto>chunk(10)
//                .reader(reader)
//                .writer(writer)
//                .build();
//    }
//
//    @Bean
//    public Job databaseCursorJob(@Qualifier("databaseCursorStep") Step databaseCursorStep,
//                                 JobBuilderFactory jobBuilderFactory) {
//        return jobBuilderFactory.get("databaseCursorJob")
//                .incrementer(new RunIdIncrementer())
//                .flow(databaseCursorStep)
//                .end()
//                .build();
//    }
//
//    ////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////
//
//
//
//
//
////    @Qualifier("billingJob")
////    @Bean
////    public Job billingJob(@Qualifier("fetchAgreementTasklet")Step fetchAgreementTasklet,
////                          @Qualifier("fetchAgreementRecurringItemsTasklet") Step fetchAgreementRecurringItemsTasklet,
////                          @Qualifier("fetchItemsHistoryTasklet") Step fetchItemsHistoryTasklet ,
////                          @Qualifier("populateAgreementStep") Step populateAgreementStep,
////                          @Qualifier("populateRecurringItemStep") Step populateRecurringItemStep ,
////                          @Qualifier("populateRecurringItemHistoryStep") Step populateRecurringItemHistoryStep ) {
////
////        return jobsBuilderFactory.get("billingJob")
////                .incrementer(new RunIdIncrementer())
////                .start(fetchAgreementTasklet)
////                .next(fetchAgreementRecurringItemsTasklet)
////                .next(fetchItemsHistoryTasklet)
////                .next(populateAgreementStep)
////                .next(populateRecurringItemStep)
////                .next(populateRecurringItemHistoryStep)
////                .end()
////                .build();
////    }
////
////    @Qualifier("populateRecurringItemStep")
////    @Bean
////    public Step populateRecurringItemStep(
////            @Qualifier("recurringItemReader") ItemReader<RecurringItemRaw> recurringItemReader,
////            @Qualifier("recurringItemProcessor") ItemProcessor<RecurringItemRaw, RecurringItem> recurringItemProcessor,
////            @Qualifier("recurringItemWriter") ItemWriter<RecurringItem> recurringItemWriter) {
////        return stepBuilderFactory.get("populateRecurringItemStep")
////                .<RecurringItemRaw, RecurringItem> chunk(10)
////                .reader(recurringItemReader)
////                .processor(recurringItemProcessor)
////                .writer(recurringItemWriter)
////                .build();
////    }
////
////    @Qualifier("recurringItemReader")
////    @Bean
////    public ItemReader<RecurringItemRaw> recurringItemReader() {
////        FlatFileItemReader<RecurringItemRaw> reader = new FlatFileItemReader<RecurringItemRaw>();
////        String file = salesforceConfiguration.getFileLocation(SalesforceConfiguration.TYPE_AGREEMENT_ITEMS);
////        LOG.info("::::::: Reading RecurringItem File ::::::: " + file);
////        reader.setResource(new FileSystemResource(file));
////        reader.setLinesToSkip(1);
////        reader.setLineMapper(new DefaultLineMapper<RecurringItemRaw>() {
////            {
////                setLineTokenizer(new DelimitedLineTokenizer() {
////                    {
////                        setNames(new String[] { "id", "name", "agreementId",
////                                "cost", "quantity" });
////                    }
////                });
////                setFieldSetMapper(new BeanWrapperFieldSetMapper<RecurringItemRaw>() {
////                    {
////                        setTargetType(RecurringItemRaw.class);
////                    }
////                });
////            }
////        });
////        return reader;
////    }
////
////    @Qualifier("recurringItemProcessor")
////    @Bean
////    public ItemProcessor<RecurringItemRaw, RecurringItem> recurringItemprocessor() {
////        return new RecurringItemProcessor();
////    }
////
////    @Qualifier("recurringItemWriter")
////    @Bean
////    public ItemWriter<RecurringItem> recurringItemWriter(DataSource dataSource) {
////        JdbcBatchItemWriter<RecurringItem> writer = new JdbcBatchItemWriter<RecurringItem>();
////        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<RecurringItem>());
////        writer.setSql(PostgresqlDBQuery.INSERT_RECURRING_ITEM_QRY);
////        writer.setDataSource(dataSource);
////        return writer;
////    }
//
////    public static void main(String[] args) {
////        List<String> list = new ArrayList<>();
////        list.add("10");
////        list.add("20");
////        list.add("30");
////        list.add("40");
////        System.out.println(MessageFormat.format("A string ({0}).", "'"+String.join("','", list)+"'"));
////        System.out.println(String.join("','", list));
////    }
//}
