package com.github.dragonhht.spring.quartz.quartz

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Component

/**
 * Description.
 * User: huang
 * Date: 18-10-8
 */
@Component
class MyJob {
    fun execute() {
        println("myJob is running...")
    }
}