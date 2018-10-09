package demo.listener

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener

/**
 * Job监听器.
 * User: huang
 * Date: 18-10-9
 */
class MyJobListener : JobListener {
    override fun getName(): String? {
        println("JobListener getName")
        return this.javaClass.name
    }

    override fun jobToBeExecuted(context: JobExecutionContext?) {
        println("${context!!.jobDetail.key.name} : 在Job将要被执行时执行")
    }

    override fun jobWasExecuted(context: JobExecutionContext?, jobException: JobExecutionException?) {
        println("${context!!.jobDetail.key.name} : 在Job被执行后执行")
    }

    override fun jobExecutionVetoed(context: JobExecutionContext?) {
        println("${context!!.jobDetail.key.name} : 在Job将要被执行时执行， 但又被TriggerListener否决时调用")
    }

}