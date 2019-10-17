package com.github.dragonhht.spring.quartz.controller

import com.github.dragonhht.spring.quartz.store.job.TestJob
import org.quartz.*
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

    val JOB_NAME = "test-job"
    val TRIGGER_NAME = "test-trigger"
    val GROUP_NAME = "group1"

    @GetMapping("/start")
    fun start(): Boolean {
        var scheduler = StdSchedulerFactory.getDefaultScheduler()
        scheduler.start()
        return true
    }

    @GetMapping("/create")
    fun create(): Boolean {
        val scheduler = StdSchedulerFactory.getDefaultScheduler()
        scheduler.start()
        val jobDetail = JobBuilder.newJob(TestJob::class.java)
                .withIdentity(JOB_NAME, GROUP_NAME)
                .build()
        val trigger = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_NAME, GROUP_NAME)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build()
        scheduler.scheduleJob(jobDetail, trigger)
        return true
    }

    @GetMapping("/update")
    fun update(): Boolean {
        val scheduler = StdSchedulerFactory.getDefaultScheduler()
        scheduler.start()
        val trigger = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_NAME, GROUP_NAME)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build()
        val date = scheduler.rescheduleJob(TriggerKey.triggerKey(TRIGGER_NAME, GROUP_NAME), trigger)
        println(date)
        return true
    }

//    @GetMapping("/del")
//    fun del(): Boolean {
//        val scheduler = StdSchedulerFactory.getDefaultScheduler()
//        scheduler.start()
//        scheduler.deleteJob(JobKey.jobKey(JOB_NAME))
//        return true
//    }
}