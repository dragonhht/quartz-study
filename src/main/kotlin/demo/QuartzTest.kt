package demo

import demo.listener.MyJobListener
import demo.listener.MyTriggerListener
import org.quartz.DateBuilder
import org.quartz.JobBuilder.newJob
import org.quartz.JobKey
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.TriggerKey
import org.quartz.impl.StdSchedulerFactory
import org.quartz.impl.matchers.EverythingMatcher
import org.quartz.impl.matchers.KeyMatcher


/**
 * Description.
 * User: huang
 * Date: 18-10-8
 */
fun main(args: Array<String>) {
    var scheduler = StdSchedulerFactory.getDefaultScheduler()

    scheduler.start()
    // define the job and tie it to our HelloJob class
    val job = newJob(HelloJob::class.java)
            .withIdentity("job1", "group1")
            .usingJobData("index", 0)
            .build()

    // Trigger the job to run now, and then repeat every 40 seconds
    val trigger = newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(1)
                    .withRepeatCount(2))
            .build()

    // Tell quartz to schedule the job using our trigger
    scheduler.scheduleJob(job, trigger)
    // 定义全局JobListener
    //scheduler.listenerManager.addJobListener(MyJobListener(), EverythingMatcher.allJobs())
    // 定义全局TriggerListener
    //scheduler.listenerManager.addTriggerListener(MyTriggerListener(), EverythingMatcher.allTriggers())
    // 将JobListener与指定的任务Job相关联
    scheduler.listenerManager.addJobListener(MyJobListener(), KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1")))
    // 将riggerListener与指定的Trigger关联
    scheduler.listenerManager.addTriggerListener(MyTriggerListener(), KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "group1")))

    //Thread.sleep(6000)
    //scheduler.shutdown()
}