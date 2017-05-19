package com.synchronization.quartz;

/**
 * Created by Kuba on 2017-05-13.
 */

import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Properties;


@Configuration
public class QuartzConfig {

    private String instanceName = "spring-boot-quartz-demo";

    private String instanceId = "AUTO";

    private String threadCount = "5";

    private Long startDelay = 0L;

    private Long repeatInterval = 60000L;

    private String description = "User mail job";

    @Autowired
    private DataSource dataSource;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {

        QuartzJobFactory sampleJobFactory = new QuartzJobFactory();
        sampleJobFactory.setApplicationContext(applicationContext);
        return sampleJobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory(applicationContext));

        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.instanceName", instanceName);
        quartzProperties.setProperty("org.quartz.scheduler.instanceId", instanceId);
        quartzProperties.setProperty("org.quartz.threadPool.threadCount", threadCount);
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(quartzProperties);
        factory.setTriggers(rsoJobTrigger().getObject(), weatherJobTrigger().getObject());
        return factory;
    }

    @Bean(name = "rsoJobTrigger")
    public SimpleTriggerFactoryBean rsoJobTrigger() {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(rsoJobDetails().getObject());
        factoryBean.setStartDelay(startDelay);
        factoryBean.setRepeatInterval(repeatInterval);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    @Bean(name = "weatherJobTrigger")
    public SimpleTriggerFactoryBean weatherJobTrigger() {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(weatherJobDetails().getObject());
        factoryBean.setStartDelay(repeatInterval);
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay().plusDays(1);
        factoryBean.setStartTime(Date.from(localDateTime.toInstant(ZoneOffset.UTC)));
        factoryBean.setRepeatInterval(repeatInterval * 24 * 60);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    @Bean(name = "rsoJobDetails")
    public JobDetailFactoryBean rsoJobDetails() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(RsoCronJob.class);
        jobDetailFactoryBean.setDescription("RsoJob");
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setName("RsoJob");
        return jobDetailFactoryBean;
    }

    @Bean(name = "weatherJobDetails")
    public JobDetailFactoryBean weatherJobDetails() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(WeatherCronJob.class);
        jobDetailFactoryBean.setDescription("WeatherJob");
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setName("WeatherJob");
        return jobDetailFactoryBean;
    }

}
