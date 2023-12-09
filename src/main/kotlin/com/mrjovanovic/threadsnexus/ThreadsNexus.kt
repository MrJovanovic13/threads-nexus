package `threads-nexus`.src.main.kotlin.com.mrjovanovic.threadsnexus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThreadsNexus

fun main(args: Array<String>) {
	runApplication<ThreadsNexus>(*args)
}
