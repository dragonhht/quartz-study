package demo

import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.impl.StdSchedulerFactory
import org.quartz.JobBuilder.*
import org.quartz.TriggerBuilder.*
import org.quartz.SimpleScheduleBuilder.*
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.JobDetail



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
            .build()

    // Trigger the job to run now, and then repeat every 40 seconds
    val trigger = newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(40)
                    .repeatForever())
            .build()

    // Tell quartz to schedule the job using our trigger
    scheduler.scheduleJob(job, trigger)
    Thread.sleep(6000)
    scheduler.shutdown()
}