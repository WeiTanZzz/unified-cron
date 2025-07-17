package helper

import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.api.model.PodBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.KubernetesClientBuilder
import pojo.JobEvent
import pojo.RuntimeType

const val NAMESPACE = "default"
val CLIENT: KubernetesClient = KubernetesClientBuilder().build()

fun launchJobPod(event: JobEvent) {
    val startTime = System.currentTimeMillis()

    val podName = event.reference.lowercase()

    val image = when (event.runtimeType) {
        RuntimeType.KOTLIN -> "kotlin-runner:latest"
    }

    val pod: Pod = PodBuilder()
        .withNewMetadata()
        .withName(podName)
        .withNamespace(NAMESPACE)
        .endMetadata()
        .withNewSpec()
        .addNewContainer()
        .withName(event.reference)
        .withImage(image)
        .withImagePullPolicy("IfNotPresent")
        .withArgs(event.scriptPath)
        .endContainer()
        .withRestartPolicy("Never")
        .endSpec()
        .build()

    println("Preparing to launch pod '${podName}' with image '${image}'..., cost: ${System.currentTimeMillis() - startTime}ms")

    try {
        println("Launching pod '${podName}'...")

        val startTimePod = System.currentTimeMillis()

        CLIENT.pods().inNamespace(NAMESPACE).resource(pod).create()
        println("✅ Pod '${podName}' launched successfully, cost: ${System.currentTimeMillis() - startTimePod}ms")
    } catch (e: Exception) {
        println("❌, cost: ${System.currentTimeMillis() - startTime}ms, error: ${e.message}")
        e.printStackTrace()
    }
}