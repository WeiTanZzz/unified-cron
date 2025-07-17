package jobs

import Job

class HelloJob1: Job{
    override fun execute(args: Array<String>) {
        println("Hello, World!")
        if (args.isNotEmpty()) {
            println("Arguments: ${args.joinToString(", ")}")
        }
    }
}