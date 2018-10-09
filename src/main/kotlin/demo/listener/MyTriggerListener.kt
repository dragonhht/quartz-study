package demo.listener

import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerListener

/**
 * Description.
 * User: huang
 * Date: 18-10-9
 */
class MyTriggerListener : TriggerListener {
    override fun triggerFired(trigger: Trigger?, context: JobExecutionContext?) {
        println("相关联的Trigger被触发，Job的execute将被执行时触发")
    }

    override fun getName(): String {
        println("MyTriggerListener : getName")
        return this::class.java.simpleName
    }

    override fun vetoJobExecution(trigger: Trigger?, context: JobExecutionContext?): Boolean {
        println("相关联的Trigger被触发，Job将被调用时，Scheduler调用该方法，可否决Job的执行，若返回true则该Job不会因此次Trigger触发而执行")
        return false
    }

    override fun triggerComplete(trigger: Trigger?, context: JobExecutionContext?, triggerInstructionCode: Trigger.CompletedExecutionInstruction?) {
        println("相关联的Trigger被触发,并且完成Job调用时执行")
    }

    override fun triggerMisfired(trigger: Trigger?) {
        println("当Trigger错过触发时调用")
    }
}