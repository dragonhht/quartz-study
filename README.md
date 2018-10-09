# Quartz学习

## 依赖（使用Gradle）

```
compile group: 'org.quartz-scheduler', name: 'quartz', version: '2.2.1'
compile group: 'org.quartz-scheduler', name: 'quartz-jobs', version: '2.2.1'
```

##  官方样例

-   定义需执行的任务类

```kotlin
/**
* 类实现Job接口中的execute方法
*/
class HelloJob: Job {
    override fun execute(context: JobExecutionContext?) {
        println("hello")
    }
}
```

-   设置运行任务

```kotlin
fun main(args: Array<String>) {
    // 获取Scheduler用于调度任务
    var scheduler = StdSchedulerFactory.getDefaultScheduler()
    // 开始执行任务
    scheduler.start()

    // 定义需执行的任务，指定我们定义的任务类
    val job = newJob(HelloJob::class.java)
            .withIdentity("job1", "group1")
            .build()

    // 定义任务的触发器
    val trigger = newTrigger()
            .withIdentity("trigger1", "group1")
            .startNow()
            .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(40)
                    .repeatForever())
            .build()

    // 告知Scheduler执行的任务及任务的触发器
    scheduler.scheduleJob(job, trigger)
    // 留点时间等待任务执行
    Thread.sleep(6000)
    // 关闭Scheduler
    scheduler.shutdown()
}
```

> 在官方示例中，通过类通过实现`Job`接口来定义任务。  
> 在Quartz中，任务的运行需要定义任务与触发器；任务用于指定需任务，触发器用于定义任务的触发时机

## Quartz中常用的一些类

-   `Job`: Job接口,任务类需要实现的接口。注意:`实现该接口的类必须存在默认的无参构造器`

-   `JobDetail`: 该类为`Job`提供了许多属性, 其中包含`JobDataMap`;Quartz不存储Job类的实际实例，而是通过使用JobDetail定义一个实例。JobDetails通过JobBuilder创建定义。

-   `JobExecutionContext`: 在实现`Job`接口的类中，实现的`execute`方法会传入JobExecutionContext的实例。调用的Job通过JobExecutionContext的实例可以访问到Quartz运行的环境及Job的一些信息等

-   `JobDataMap`: 可用于装载任何可序列化的数据。JobDataMap将存储在JobExecutionContext中，任务可通过JobExecutionContext实例来获取JobDataMap中的数据;(JobDataMap底层采用Map的数据结构)，如：

    -   通过JobDataMap传递数据给Job

    ```kotlin
    // 简单的传递键为hello，值为world的数据，该数据将会存放在JobDataMap中
    val job = newJob(HelloJob::class.java)
                .withIdentity("job1", "group1")
                .usingJobData("hello", "world")
                .build()
    ```
    
    -   Job通过JobExecutionContext获取JobDataMap中的数据
    
    ```kotlin
    class HelloJob: Job {
        override fun execute(context: JobExecutionContext?) {
            val name = context!!.jobDetail.key.name
            // 获取JobDataMap中的数据，取值也可在Job类中定义与键对应的属性，这样也可获取数据
            val hello = context.jobDetail.jobDataMap["hello"]
            println("$name is running, $hello")
        }
    }
    ```

## 有状态的Job与无状态的Job

> 在Quartz中，Job可能会持有某些状态信息，例如在Job中的index属性用于计算任务的调用次数，这些信息将被存储在JobDataMap，这时候便就是`有状态Job`  
> 无状态Job在每次运行时都会创建新的JobDataMap，即同一任务的后一次调用无法获取前一次调用保存的信息

-   将设置Job为有状态

> 若需将Job设置为有状态，只需在Job类上使用`@PersistJobDataAfterExecution`注解，这样任务在多次运行时则不会重新创建新的JobDataMap

-   示例

    -   传递index值给Job
    
    ```kotlin
    val job = newJob(HelloJob::class.java)
                .withIdentity("job1", "group1")
                .usingJobData("index", 0)
                .build()
    ```
    
    -   将Job设置为有状态，并将index的值放入JobDataMap中(该示例通过在任务类中定义属性来获取JobDataMap的数据)
    
    ```kotlin
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
    ```

    
