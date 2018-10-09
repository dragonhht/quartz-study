package demo

import org.quartz.DateBuilder
import org.quartz.JobBuilder.newJob
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory


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
    //Thread.sleep(6000)
    //scheduler.shutdown()
}