package com.pay.settlement.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SettlementBatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job settlementJob;

    @Scheduled(cron = "0 30 23 * * ?")
    public void runSettlementJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();


        // settlementJob 을
        // jobParameters 와 함께 실행한다.
        jobLauncher.run(settlementJob, jobParameters);
    }


}
