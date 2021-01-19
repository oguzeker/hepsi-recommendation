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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseCursorJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCursorJobLauncher.class);

    private final Job job;
    private final JobLauncher jobLauncher;
    private final ResettableCountDownLatch resettableCountDownLatch;

    @Autowired
    public DatabaseCursorJobLauncher(@Qualifier("databaseCursorJob") Job job,
                                     JobLauncher jobLauncher,
                                     ResettableCountDownLatch resettableCountDownLatch) {
        this.job = job;
        this.jobLauncher = jobLauncher;
        this.resettableCountDownLatch = resettableCountDownLatch;
    }

    @Scheduled(initialDelay = 100, fixedRate = 100)
//    @Scheduled(cron = "1/1 * * * * *")
    public void runDatabaseCursorJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException {
        LOGGER.info("Database cursor job BEGIN");

        jobLauncher.run(job, newExecution());
        resettableCountDownLatch.countDown();
        resettableCountDownLatch.reset();

        LOGGER.info("Database cursor job END");
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }

}
