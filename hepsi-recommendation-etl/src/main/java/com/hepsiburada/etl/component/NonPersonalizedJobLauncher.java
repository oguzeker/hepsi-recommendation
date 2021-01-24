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
public class NonPersonalizedJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NonPersonalizedJobLauncher.class);

    private final Job job;
    private final JobLauncher jobLauncher;

    @Autowired
    public NonPersonalizedJobLauncher(Job nonPersonalizedJob,
                                      JobLauncher asyncJobLauncher) {
        this.job = nonPersonalizedJob;
        this.jobLauncher = asyncJobLauncher;
    }

    @Scheduled(fixedDelay = 1500)
    public void runNonPersonalizedJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("Database cursor job BEGIN");

        jobLauncher.run(job, newExecution());

        LOGGER.info("Database cursor job END");
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }

}
