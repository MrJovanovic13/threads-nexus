package com.mrjovanovic.threadsnexus.config

import com.mrjovanovic.threadsnexus.model.Command
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Configuration
class CommandsSinkConfig {

    @Bean
    fun commandsSink(): Sinks.Many<Command> {
        return Sinks.many().multicast().onBackpressureBuffer(20)
    }

    @Bean
    fun commandsFlux(): Flux<Command> {
        return commandsSink().asFlux()
    }
}
