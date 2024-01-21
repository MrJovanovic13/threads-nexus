package com.mrjovanovic.threadsnexus.config

import com.mrjovanovic.threadsnexus.model.Device
import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceStatus
import com.mrjovanovic.threadsnexus.model.enumeration.DeviceType
import com.mrjovanovic.threadsnexus.model.enumeration.Severity
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.time.Duration
import java.time.Instant

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

    @EventListener(ApplicationReadyEvent::class)
    fun initializeSSEHeartbeat() {
        Flux.interval(Duration.ofSeconds(10))
            .doOnNext { emitHeartbeatEvent() }
            .subscribe()
    }

    private fun emitHeartbeatEvent() {
        val device = Device("BACKEND_SERVER", "BACKEND_SERVER", DeviceType.UNKNOWN, DeviceStatus.ONLINE, null, null)
        val deviceEvent = DeviceEvent(Instant.now().toString(), "HEARTBEAT", null, Instant.now(), Severity.LOW, device)
        deviceEventsSink().tryEmitNext(deviceEvent)
    }
}
