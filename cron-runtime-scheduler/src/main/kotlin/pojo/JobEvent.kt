package pojo

import kotlinx.serialization.Serializable


@Serializable
enum class RuntimeType {
    KOTLIN
}

@Serializable
data class JobEvent(
    val reference: String,
    val jobReference: String,
    val runtimeType: RuntimeType,
    val scriptPath: String,
)