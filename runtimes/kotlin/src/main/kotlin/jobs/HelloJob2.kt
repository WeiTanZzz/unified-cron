package jobs

import Job


class HelloJob2: Job{
    override fun execute(args: Array<String>) {
        println("Hello, World2!")
        if (args.isNotEmpty()) {
            println("Arguments: ${args.joinToString(", ")}")
        }
    }
}