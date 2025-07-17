fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: java -jar runner.jar <fully.qualified.JobClassName> [args...]")
        return
    }

    val className = args[0]
    val jobArgs = args.drop(1).toTypedArray()

    try {
        val clazz = Class.forName(className)
        val instance = clazz.getDeclaredConstructor().newInstance() as Job
        instance.execute(jobArgs)
    } catch (e: Exception) {
        println("Failed to load or execute job class: $className")
        e.printStackTrace()
    }
}

interface Job {
    fun execute(args: Array<String>)
}