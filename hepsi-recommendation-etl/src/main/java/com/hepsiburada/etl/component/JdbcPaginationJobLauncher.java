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
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This bean schedules and runs the Spring Batch job which
 * reads the input data from the database by using a database
 * cursor.
 */
@Component
public class JdbcPaginationJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPaginationJobLauncher.class);

    private final Job job;
    private final JobLauncher jobLauncher;
    private final ResettableCountDownLatch resettableCountDownLatch;

    @Autowired
    public JdbcPaginationJobLauncher(@Qualifier("jdbcPaginationJob") Job job, JobLauncher jobLauncher,
                                     ResettableCountDownLatch resettableCountDownLatch) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.resettableCountDownLatch = resettableCountDownLatch;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void runSpringBatchExampleJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("JDBC pagination job BEGIN");

        jobLauncher.run(job, newExecution());

        LOGGER.info("JDBC pagination job END");
    }

//    @Async
    @Scheduled(cron = "0/1 * * * * *")
    public void asd() {
        LOGGER.info("ASD BEGIN");



//        System.out.println(" >>>> ters yüz ediliyooooorrrr: " + lockingComponent.isLocked()
//                + " -->> " + !lockingComponent.isLocked());
//        lockingComponent.setLocked(!lockingComponent.isLocked());

        resettableCountDownLatch.countDown();
        resettableCountDownLatch.reset();

        LOGGER.info("ASD END");
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }
}
