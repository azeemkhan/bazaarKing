package com.tresmoto.batch.jobs;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(QuartzJob.class);

    private static int negationFactor = 4;
    private int negationIndex = 0;
    private String jobName;
    private JobLauncher jobLauncher;
    private JobLocator jobLocator;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public JobLocator getJobLocator() {
        return jobLocator;
    }

    public void setJobLocator(JobLocator jobLocator) {
        this.jobLocator = jobLocator;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobContext) throws JobExecutionException {
        try {
            log.info("--------------------------------------------------------------------");
            log.info("payment engine job start: {}", jobContext.getFireTime());
            JobDetail jobDetail = jobContext.getJobDetail();
            log.info("payment engine job name is: {} ", jobDetail.getJobDataMap().getString("jobName"));
            log.info("MyJob end: {} , key: {} ", jobContext.getJobRunTime(), jobDetail.getKey());
            log.info("payment engine job next scheduled time: {} ", jobContext.getNextFireTime());
            log.info("--------------------------------------------------------------------");
            log.info("payment engine Job's thread name is: {} ", Thread.currentThread().getName());

            ApplicationContext applicationContext = (ApplicationContext)
                    jobContext.getScheduler().getContext().get("applicationContext");

            jobLocator = (JobLocator) applicationContext.getBean(JobLocator.class);
            jobLauncher = (JobLauncher) applicationContext.getBean(JobLauncher.class);
            Job job = jobLocator.getJob(jobName);

            if ((negationIndex % negationFactor) == 0) {
                ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
                Cache cache = cacheManager.getCache("configurationCache");
                if (cache != null) {
                    cache.clear();
                }
                negationIndex = 0;
            }
            negationIndex++;
            JobParameters params = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(job, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
