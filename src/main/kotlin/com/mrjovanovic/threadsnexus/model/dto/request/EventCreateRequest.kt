package com.mrjovanovic.threadsnexus.model.dto.request

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.enumeration.Severity
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.Instant

data class EventCreateRequest(
    val title: String,
    val info: String?,
    val timestamp: Instant,
    val severity: Severity?,
    val device: DeviceCreateRequest
)

fun Mono<EventCreateRequest>.toEntity() = flatMap {
    it.toEntity().toMono()
}

fun EventCreateRequest.toEntity() =
    DeviceEvent(
        id = null,
        title = this.title,
        info = this.info,
        timestamp = this.timestamp,
        severity = this.severity,
        device = this.device.toEntity()
    )
