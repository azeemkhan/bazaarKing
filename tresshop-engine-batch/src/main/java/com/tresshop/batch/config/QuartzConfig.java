package com.tresmoto.batch.config;

import com.tresmoto.batch.jobs.QuartzJob;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    private static final Logger log = LoggerFactory.getLogger(QuartzConfig.class);

    @Value("${expiring.alert.payment-transaction.cycle}")
    private int paymentTransactionAlertCycle;

    @Value("${expiring.alert.event-handler.cycle}")
    private int eventHandlerAlertCycle;

    @Value("${expiring.alert.s2s-event-handler.cycle}")
    private int s2sEventHandlerAlertCycle;

    @Value("${expiring.alert.adonp.cycle}")
    private int expiringAlertADONPCycle;

    @Value("${activation.cycle}")
    private int activationCycle;

    @Value("${expired.cycle}")
    private int expiredCycle;

    @Value("${recently.updated.cycle}")
    private int recentlyUpdatedCycle;

    @Value("${promo.active.cycle}")
    private int promoActivateCycle;

    @Value("${promo.expire.cycle}")
    private int promoExpireCycle;

    @Value("${process-burn.cycle}")
    private int processBurnCycle;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;


    @Value("/application-${spring.profiles.active}.yml")
    private String activeProfile;


    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }


    @Bean
    public JobDetail eventHandlerJobDetail() {
        log.debug("create job eventHandlerJob");
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "eventHandlerJob");
        return JobBuilder.newJob(QuartzJob.class).withIdentity("eventHandlerJob")
                .setJobData(jobDataMap).storeDurably().build();
    }


    @Bean
    public Trigger eventHandlerTrigger() {
        log.debug("create eventHandlerTrigger");
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInMinutes(eventHandlerAlertCycle)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(eventHandlerJobDetail())
                .withIdentity("eventHandlerTrigger").withSchedule(scheduleBuilder).build();
    }

    @Bean
    public JobDetail s2sEventHandlerJobDetail() {
        log.debug("create job s2sEventHandlerJob");
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "s2sEventHandlerJob");
        return JobBuilder.newJob(QuartzJob.class).withIdentity("s2sEventHandlerJob")
                .setJobData(jobDataMap).storeDurably().build();
    }


    @Bean
    public Trigger s2sEventHandlerTrigger() {
        log.debug("create s2sEventHandlerTrigger");
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInMinutes(s2sEventHandlerAlertCycle)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(s2sEventHandlerJobDetail())
                .withIdentity("s2sEventHandlerTrigger").withSchedule(scheduleBuilder).build();
    }


    @Bean
    public JobDetail adonpReportJobDetail() {
        log.debug("create job adonpReportJob");
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "adonpReportJob");
        return JobBuilder.newJob(QuartzJob.class).withIdentity("adonpReportJob")
                .setJobData(jobDataMap).storeDurably().build();
    }


    @Bean
    public Trigger adonpReportTrigger() {
        log.debug("create adonpReportTrigger");
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInMinutes(expiringAlertADONPCycle)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(adonpReportJobDetail())
                .withIdentity("adonpReportTrigger").withSchedule(scheduleBuilder).build();
    }

    @Bean
    public JobDetail processTransactionJobDetail() {
        log.debug("create job processTransactionJob");
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", "processTransactionJob");
        return JobBuilder.newJob(QuartzJob.class).withIdentity("processTransactionJob")
                .setJobData(jobDataMap).storeDurably().build();
    }


    @Bean
    public Trigger processTransactionTrigger() {
        log.debug("create processTransactionTrigger");
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInMinutes(paymentTransactionAlertCycle)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(processTransactionJobDetail())
                .withIdentity("processTransactionTrigger").withSchedule(scheduleBuilder).build();
    }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        log.debug("schedulerFactoryBean is going to be set");
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(s2sEventHandlerTrigger(), eventHandlerTrigger(), processTransactionTrigger(), adonpReportTrigger());
        scheduler.setQuartzProperties(quartzProperties());
        scheduler.setJobDetails(s2sEventHandlerJobDetail(), eventHandlerJobDetail(), processTransactionJobDetail(), adonpReportJobDetail());
        scheduler.setApplicationContextSchedulerContextKey("applicationContext");
        return scheduler;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        log.debug("--------------------------------------------------------------------");
        log.info("\t Starting payment engine batch processor quartzProperties");
        log.info(" the present profile is {} " + activeProfile);
        log.debug("--------------------------------------------------------------------");
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(activeProfile));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}
