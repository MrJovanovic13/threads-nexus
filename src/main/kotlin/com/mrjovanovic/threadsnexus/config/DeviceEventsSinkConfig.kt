package com.mrjovanovic.threadsnexus.config

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Configuration
class DeviceEventsSinkConfig {

    @Bean
    fun deviceEventsSink(): Sinks.Many<DeviceEvent> {
        return Sinks.many().multicast().onBackpressureBuffer(20)
    }

    @Bean
    fun deviceEventsFlux(): Flux<DeviceEvent> {
        return deviceEventsSink().asFlux()
            .cache(0)
    }
}
