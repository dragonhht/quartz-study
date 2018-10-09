package demo

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution

/**
 * 定义任务.
 * User: huang
 * Date: 18-10-8
 */
@PersistJobDataAfterExecution
class HelloJob: Job {

    var index = 0

    override fun execute(context: JobExecutionContext?) {
        val name = context!!.jobDetail.key.name
        println("$name is running, index is $index")
        index++
        // 存放数据到JobDataMap中
        context.jobDetail.jobDataMap["index"] = index
    }
}