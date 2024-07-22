package com.mrjovanovic.threadsnexus.model.dto.response

import com.mrjovanovic.threadsnexus.model.DeviceEvent
import com.mrjovanovic.threadsnexus.model.enumeration.Severity
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.Instant

data class EventResponse(
    val id: String?,
    val title: String,
    val info: String?,
    val timestamp: Instant,
    val severity: Severity?,
    val device: DeviceResponse
)

fun Mono<DeviceEvent>.toEventResponse() = flatMap {
    it.toEventResponse().toMono()
}

fun DeviceEvent.toEventResponse() =
    EventResponse(
        id = this.id,
        title = this.title,
        info = this.info,
        timestamp = this.timestamp,
        severity = this.severity,
        device = this.device.toDeviceResponse()
    )
