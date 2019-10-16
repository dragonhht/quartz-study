package com.github.dragonhht.spring.quartz.controller

import com.github.dragonhht.spring.quartz.store.job.TestJob
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.Scheduler
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * .
 *
 * @author: dragonhht
 * @Date: 2019-10-16
 */
@RestController
class TestController {

    @GetMapping("/start")
    fun start(): Boolean {
        var scheduler = StdSchedulerFactory.getDefaultScheduler()
        scheduler.start()
        return true
    }

    @GetMapping("/create")
    fun create(): Boolean {
        var scheduler = StdSchedulerFactory.getDefaultScheduler()
        scheduler.start()
        var jobDetail = JobBuilder.newJob(TestJob::class.java)
                .withIdentity("test-job", "group1")
                .build()
        var trigger = TriggerBuilder.newTrigger()
                .withIdentity("test-trigger", "group1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build()
        scheduler.scheduleJob(jobDetail, trigger)
        return true
    }


}