package com.github.dragonhht.spring.quartz.store.job

import org.quartz.Job
import org.quartz.JobExecutionContext

/**
 * .
 *
 * @author: dragonhht
 * @Date: 2019-10-16
 */
class TestJob: Job {
    override fun execute(context: JobExecutionContext?) {
        println("执行任务TestJob")
    }
}