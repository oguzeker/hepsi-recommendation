package com.hepsiburada.etl.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcPagingJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPagingJobLauncher.class);

    private final Job job;
    private final JobLauncher jobLauncher;
    private final ResettableCountDownLatch resettableCountDownLatch;

    @Autowired
    public JdbcPagingJobLauncher(@Qualifier("jdbcPagingJob") Job job,
                                 @Qualifier("asyncJobLauncher") JobLauncher jobLauncher,
                                 ResettableCountDownLatch resettableCountDownLatch) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.resettableCountDownLatch = resettableCountDownLatch;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 175000)
//    @Scheduled(cron = "0 0/3 * * * *")
//    @Scheduled(cron = "0/1 * * * * *")
    public void runJdbcPagingJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("JDBC paging job BEGIN");

        jobLauncher.run(job, newExecution());

        LOGGER.info("JDBC paging job END");
    }

//    @Scheduled(fixedRate = 100)
//    public void asd() {
//        LOGGER.info("ASD BEGIN");
//
//        resettableCountDownLatch.countDown();
//        resettableCountDownLatch.reset();
//
//        LOGGER.info("ASD END");
//    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }

    @Autowired
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    public void stop() {
        postProcessor.postProcessBeforeDestruction(job, "jdbcPagingJob");
    }

    public void start() {
        postProcessor.postProcessAfterInitialization(job, "jdbcPagingJob");
    }

}
