import com.cronutils.model.Cron
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

val cronMap = ConcurrentHashMap<String, ConcurrentLinkedQueue<JobConfig>>()

fun main() {
    append1MJobConfigs()

    var count = 0

    val startTime = System.currentTimeMillis()

    cronMap.forEach { (key, value) ->
        val cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX)
        val parser = CronParser(cronDefinition)
        val cron = parser.parse(key)

        value.forEach { jobConfig ->
            if (shouldRun(cron, jobConfig.lastExecution)) {
//                println("Job with cron '$key' should run at ${ZonedDateTime.now()}")
            } else {
//                println("Job with cron '$key' should NOT run at ${ZonedDateTime.now()}")
            }
            count++
        }
    }

    //one thread always below 10s
    println("Total jobs checked: $count, Time taken: ${System.currentTimeMillis() - startTime} ms")
}

fun append1MJobConfigs() {
    val cron = "* * * * *"
    val lastExecution = ZonedDateTime.now().minus(10, ChronoUnit.MINUTES)
    val jobConfig = JobConfig(cron, lastExecution)
    repeat(1_000_000) {
        cronMap.computeIfAbsent(cron) { ConcurrentLinkedQueue<JobConfig>() }.add(jobConfig)

    }
}

data class JobConfig(
    val cronExpression: String,
    val lastExecution: ZonedDateTime
)

fun shouldRun(cron: Cron, lastExecution: ZonedDateTime): Boolean {
    val executionTime = ExecutionTime.forCron(cron)
    val nextExecution = executionTime.nextExecution(lastExecution).orElse(null) ?: return false

    return nextExecution.isBefore(ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES))
}