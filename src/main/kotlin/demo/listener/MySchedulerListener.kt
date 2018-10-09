package demo.listener

import org.quartz.*

/**
 * Description.
 * User: huang
 * Date: 18-10-10
 */
class MySchedulerListener : SchedulerListener {
    override fun jobScheduled(trigger: Trigger?) {
        println("${trigger!!.key.name} 在部署JobDetail时调用")
    }

    override fun jobUnscheduled(triggerKey: TriggerKey?) {
        println("${triggerKey!!.name} 在卸载JobDetail时调用")
    }

    override fun schedulerShuttingdown() {

    }

    override fun schedulerStarting() {

    }

    override fun jobAdded(jobDetail: JobDetail?) {

    }

    override fun schedulingDataCleared() {

    }

    override fun triggerFinalized(trigger: Trigger?) {
        println("${trigger!!.key.name} 当Trigger到了不再会被触发时，若Job未设置为持久性，则会从scheduler中移除")
    }

    override fun triggerPaused(triggerKey: TriggerKey?) {

    }

    override fun schedulerError(msg: String?, cause: SchedulerException?) {

    }

    override fun jobPaused(jobKey: JobKey?) {

    }

    override fun triggersPaused(triggerGroup: String?) {

    }

    override fun schedulerShutdown() {

    }

    override fun triggerResumed(triggerKey: TriggerKey?) {

    }

    override fun jobResumed(jobKey: JobKey?) {

    }

    override fun jobsResumed(jobGroup: String?) {

    }

    override fun jobDeleted(jobKey: JobKey?) {

    }

    override fun schedulerInStandbyMode() {

    }

    override fun schedulerStarted() {

    }

    override fun triggersResumed(triggerGroup: String?) {

    }

    override fun jobsPaused(jobGroup: String?) {

    }
}