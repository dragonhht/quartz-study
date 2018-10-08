package com.github.dragonhht.spring.quartz.config

import com.github.dragonhht.spring.quartz.quartz.MyJob
import org.quartz.CronTrigger
import org.quartz.JobDetail
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import java.util.stream.Collectors.toList

/**
 * Description.
 * User: huang
 * Date: 18-10-8
 */
@Configuration
open class QuartzConfig {

    @Bean
    open fun jobDetailFactoryBean(myJob: MyJob): MethodInvokingJobDetailFactoryBean {
        var detail = MethodInvokingJobDetailFactoryBean()
        detail.targetObject = myJob
        detail.targetMethod = "execute"
        detail.setConcurrent(false)
        return detail
    }

    @Bean
    open fun cronTriggerBean(jobDetailFactoryBean: MethodInvokingJobDetailFactoryBean): CronTriggerFactoryBean {
        var cronTrigger = CronTriggerFactoryBean()
        cronTrigger.setJobDetail(jobDetailFactoryBean.`object`)
        cronTrigger.setCronExpression("0/1 * * * * ?")
        return cronTrigger
    }

    @Bean
    @Lazy(false)
    open fun startQuertz(cronTriggerFactoryBeans: List<CronTriggerFactoryBean>): SchedulerFactoryBean {
        var scheduler = SchedulerFactoryBean()
        var treggers =
                cronTriggerFactoryBeans.stream()
                        .map(CronTriggerFactoryBean::getObject)
                        .collect(toList())
        scheduler.setTriggers(*treggers.toTypedArray())
        return scheduler
    }

}