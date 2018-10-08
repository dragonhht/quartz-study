package demo

import org.quartz.Job
import org.quartz.JobExecutionContext

/**
 * Description.
 * User: huang
 * Date: 18-10-8
 */
class HelloJob: Job {
    override fun execute(context: JobExecutionContext?) {
        println("hello")
    }
}