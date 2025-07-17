import helper.createJsonDeserializer
import helper.launchJobPod
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import pojo.JobEvent
import java.time.Duration
import java.util.*

fun main() {
    val props = Properties()
    props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = System.getenv("BOOTSTRAP_SERVERS")
    props[ConsumerConfig.GROUP_ID_CONFIG] = "cron-runtime-scheduler"
    props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"


    val consumer = KafkaConsumer(props, StringDeserializer(), createJsonDeserializer<JobEvent>())

    consumer.subscribe(listOf("cron-job-trigger"))

    while (true) {
        val records = consumer.poll(Duration.ofMillis(1000))
        for (record in records) {
            println("Received record: $record")
            try {
                launchJobPod(record.value())
            } catch (_: Exception) {
                println("Error processing record: ${record.key()}")
            }
        }
        consumer.commitSync()
    }
}